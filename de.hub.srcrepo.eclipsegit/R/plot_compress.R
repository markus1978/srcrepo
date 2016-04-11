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
}

data <- importData("16-04-06-big-summary")

checkout = data$X3_checkoutTimeSum
parse = pmax(data$X4_refreshTimeSum+data$X5_importTimeSum-data$X6_writeTimeSum,0)
write = data$X6_writeTimeSum
read = (data$FragLoadETSum)
snapshot = data$RevComputeSSETSum
udf = data$RevUDFETSum
import = checkout + parse + write
analysis = read + snapshot + udf
data$diff = import - analysis

asNumeric <- function(f) as.numeric(levels(f))[as.integer(f)]
factorsNumeric <- function(d) modifyList(d, lapply(d[, sapply(d, is.factor)], asNumeric))
data <- factorsNumeric(data)

data <- data[with(data, order(-FullSizeSum)),]

colnames(data)[which(names(data) == "X1_name")] <- "name"

edata <- data[5:9,] #head(data, 5)

rebind_5 = c(1,6,2,7,3,8,4,9,5,10)
rebind_10 = c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)

rebind = rebind_5

bar_names <- sub("^git.eclipse.org-([^-]*-)+","",edata$name,perl=TRUE)
bar_names <- sub("\\.git", "", bar_names)
bar_names <- sub("Eclipse_Foundation\\.", "", bar_names)
bar_names <- sub("platform\\.", "", bar_names)
bar_names <- sub("\\.runtime", "", bar_names)
bar_names <- sub("\\.common", "", bar_names)
bar_names <- sub("eclipse\\.", "", bar_names)

shorten = function(name) {
  if (nchar(name) > 7) {
    short = substr(name, nchar(name) - 5, nchar(name))
    paste(c("...", short), collapse="")
  } else {
    name
  } 
}

plotCompressData = function(bar_title, unit, full, uc, delta) {
  bar_data <- cbind(rbind(full, 0, 0), rbind(0, uc, delta))
  bar_data <- bar_data[,rebind]
  
  bar_lengend <- c("all", "uncompressed", "compressed")
  barplot(
    bar_data,
    names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
    main=bar_title,
    ylab=unit,las=2,
    legend = bar_lengend,
    col = rainbow(3, s = 0.65),
    args.legend = list(x = "topright", cex = 1),
    space=c(.75,0))
}

G=1/1000000000
M=1/1000000
attach(mtcars)

setwd("/Users/markus/Documents/Forschung/Paper/papers/Models2016/sources/figures/")
pdf("compress.pdf", width=9, height=5.5)
layout(matrix(c(1,2,3,4,5,6), 2, 3, byrow = TRUE))
plotCompressData("NamedElement-only Matcher", "GB", edata$FullSizeSum*G, edata$UCSizeSum*G, edata$DeltaSizeNamedElementSum*G)
plotCompressData("Meta Class Matcher", "GB", edata$FullSizeSum*G, edata$UCSizeSum*G, edata$DeltaSizeMetaClassSum*G)
plotCompressData("Lines", "MLines", edata$FullLineCountSum*M, edata$UCLineCountSum*M, (edata$AddedLinesSum+edata$RemovedLinesSum)*M)

#plotCompressData("All vs Matched - NamedElement-only Matcher", "MObjects", data$FullObjectCountSum*M, data$UCObjectCountSum*M, data$MatchedObjectsCountNamedElementSum*M)
#plotCompressData("All vs Matched - Meta Class Matcher", "MObjects", data$FullObjectCountSum*M, data$UCObjectCountSum*M, data$MatchedObjectsCountMetaClassSum*M)
#plotCompressData("All vs Matched Lines", "MLines", data$FullLineCountSum*M, data$UCLineCountSum*M, data$MatchedLineCountSum*M)

d = data
rUC = d$UCSizeSum/d$FullSizeSum
rDNM = d$DeltaSizeNamedElementSum/d$FullSizeSum
rDMC = d$DeltaSizeMetaClassSum/d$FullSizeSum
rL = (d$AddedLinesSum+d$RemovedLinesSum)/d$FullLineCountSum

bpDf = data.frame(rUC, rDNM, rDMC, rL)
cols = rainbow(3, s=0.65)
boxplot(
  bpDf*100,
  names = c("uncompressed", "n.e. only", "meta class", "line diff"),
  outline = FALSE,
  col = c(cols[2], cols[3], cols[3], cols[3]),
  main="Compressed rel. to full size",
  ylab="(%)"
) 

# rel = 3600000000
# parse = (edata$X4_refreshTimeSum+edata$X5_importTimeSum-edata$X6_writeTimeSum)/rel
# cMC = edata$CompressETMetaClassSum/(rel*1000)
# cNM = edata$CompressETNamedElementSum/(rel*1000)
# pMC = edata$PatchETMetaClassSum/(rel*1000)
# pNM = edata$PatchETNamedElementSum/(rel*1000)
# 
# bar_data <- cbind(rbind(parse, cMC, 0), rbind(0, 0, pMC))
# bar_data <- bar_data[,rebind]
# bar_lengend <- c("parse", "compress", "decompress")
# barplot(
#   bar_data,
#   names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
#   main="Execution time for example projects",
#   ylab="time (hours)",las=2,
#   legend = bar_lengend,
#   col = rainbow(4, s = 0.65),
#   args.legend = list(x = "topright", cex = 1),
#   space=c(.75,0)
# )

rel = data$X1_revCount*1000
parse = pmax(data$X4_refreshTimeSum+data$X5_importTimeSum-data$X6_writeTimeSum,0)/rel
cMC = data$CompressETMetaClassSum/(rel*1000)
cNM = data$CompressETNamedElementSum/(rel*1000)
pMC = data$PatchETMetaClassSum/(rel*1000)
pNM = data$PatchETNamedElementSum/(rel*1000)

bpDf = data.frame(parse, cNM, cMC, pNM, pMC)
boxplot(
  bpDf,
  #names = c("uncompressed", "n.e. only", "meta class", "line diff"),
  outline = FALSE,
  #col = c(cols[2], cols[3], cols[3], cols[3]),
  main="Avg. execution times",
  ylab="avg. time per revision (ms)"
) 

rel = data$X1_revCount*1000
parse = pmax(data$X4_refreshTimeSum+data$X5_importTimeSum-data$X6_writeTimeSum,0)/rel
cMC = data$CompressETMetaClassSum/(rel*1000)
cNM = data$CompressETNamedElementSum/(rel*1000)
pMC = (data$PatchETMetaClassSum/(rel*1000))+10
pNM = (data$PatchETNamedElementSum/(rel*1000))+10

bpDf = data.frame(parse, cNM, cMC, pNM, pMC)
boxplot(
  bpDf,
  #names = c("uncompressed", "n.e. only", "meta class", "line diff"),
  outline = FALSE,
  #col = c(cols[2], cols[3], cols[3], cols[3]),
  main="Avg. execution times (logarithmic)",
  ylab="avg. time per revision (ms)",
  log = "y", ylim = c(10,3000)
) 
dev.off()
