setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")

importData = function(experiment_id) {
  data_file <- paste(c(experiment_id, "/data/importdata.csv"), collapse="")
  import_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")
  data_file <- paste(c(experiment_id, "/data/metrics.csv"), collapse="")
  metrics_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")
  data_file <- paste(c(experiment_id, "/data/compress.csv"), collapse="")
  compress_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")
  
  colnames(metrics_data)[which(names(metrics_data) == "name")] <- "X1_name"
  colnames(compress_data)[which(names(compress_data) == "name")] <- "X1_name"
  import_data$X1_name <- sub("Eclipse_Foundation\\.", "", import_data$X1_name)  
  
  data <- merge(import_data,metrics_data,by="X1_name", all=TRUE)
  data <- merge(data,compress_data,by="X1_name", all=TRUE)
  data <- na.omit(data)
  data <- data[with(data, order(-X1_cuCount)),]
  data <- data[data$X1_cuCount>0,]
  
  asNumeric <- function(f) as.numeric(levels(f))[as.integer(f)]
  factorsNumeric <- function(d) modifyList(d, lapply(d[, sapply(d, is.factor)], asNumeric))
  data <- factorsNumeric(data)
}

data <- importData("16-04-06-big-summary")

relative = function(data, rel) {
  names = data$X1_name
  checkout = data$X3_checkoutTimeSum/rel
  parse = pmax(data$X4_refreshTimeSum+data$X5_importTimeSum-data$X6_writeTimeSum,0)/rel
  write = data$X6_writeTimeSum/rel
  read = (data$FragLoadETSum)/rel
  snapshot = data$RevComputeSSETSum/rel
  udf = data$RevUDFETSum/rel
  import = checkout + parse + write
  analysis = read + snapshot + udf
  diff = import - analysis
  all = import + analysis
  
  compress = data$CompressETMetaClassSum/(rel*1000)
  patch = data$PatchETMetaClassSum/(rel*1000)
  
  compress_rel = (data$UCSizeSum+data$DeltaSizeMetaClassSum)/data$FullSizeSum
  cwrite = write*compress_rel
  cread = read*compress_rel
  csnapshot = snapshot*compress_rel
  
  result <- data.frame(checkout, parse, write, read, snapshot, udf, names, import, analysis, all, diff, cwrite, cread, csnapshot, compress, patch, compress_rel)
  result
}

result = relative(data, data$X1_revCount*1000) 
#colors <- c("red", "red", "red", "blue", "blue","blue")
colors <- rainbow(6, s = 0.65)

#layout(matrix(c(2,1), 1, 2, byrow = TRUE))
setwd("/Users/markus/Documents/Forschung/Paper/papers/Models2016/sources/figures/")
pdf("et_boxplot.pdf", width=5.5, height=4)
boxplot(
  result$checkout, result$parse, result$write, result$read, result$snapshot, result$udf,
  names = c("checkout", "parse", "write", "read", "snapshot", "udf"),
  outline = FALSE,
  col = colors,
#main="Avg. Execution Times",
  ylab="avg time per revision (ms)"
)
dev.off()

result = relative(data, 3600000000)
examples <- result[with(result, order(-diff)),]
examples <- head(examples, 10)

bar_data <- cbind(rbind(examples$checkout, examples$parse, examples$write, 0, 0, 0),rbind(0,0,0,examples$read, examples$snapshot, examples$udf))
bar_data <- bar_data[,c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)]

bar_names <- sub("^git.eclipse.org-([^-]*-)+","",examples$names,perl=TRUE)
bar_names <- sub("\\.git", "", bar_names)
bar_names <- sub("Eclipse_Foundation\\.", "", bar_names)
bar_names <- sub("platform\\.", "", bar_names)
bar_names <- sub("\\.runtime", "", bar_names)
bar_names <- sub("eclipse\\.", "", bar_names)
bar_lengend <- sub("X[0-9]_","", c("checkout", "parse", "write", "read", "snapshot", "udf"))

pdf("et_barplot.pdf", width=5.5, height=5.5)
barplot(
  bar_data,
  names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
#  main="Execution Times for Example Projects",
  ylab="time (hours)",las=2,
  legend = bar_lengend,
  col = rainbow(6, s = 0.65),
  args.legend = list(x = "topright", cex = 1),
  ylim = c(0, 15),
  space=c(.75,0))
dev.off()

bar_data <- cbind(rbind(examples$checkout, examples$parse, examples$compress, examples$cwrite, 0, 0, 0), rbind(0,0,0,0, examples$cread, examples$csnapshot, examples$udf))
bar_data <- bar_data[,c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)]
bar_lengend <- sub("X[0-9]_","", c("checkout", "parse", "compress", "write", "read", "snapshot", "udf"))
pdf("et_withCompression.pdf", width=5.5, height=5.5)
barplot(
  bar_data,
  names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
#  main="With Hypothetical Delta-Compression",
  ylab="time (hours)",las=2,
  legend = bar_lengend,
  col = rainbow(7, s = 0.65),
  args.legend = list(x = "topright", cex = 1),
  ylim = c(0, 15),
  space=c(.75,0))
dev.off()