setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")
experiment_id = "16-03-21-testdrive-2-compress"
data_file <- paste(c(experiment_id, "/data/compress.csv"), collapse="")
compress_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")

data_file <- paste(c(experiment_id, "/data/importdata.csv"), collapse="")
import_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")

colnames(import_data)[which(names(import_data) == "X1_name")] <- "name"
import_data$name <- sub("Eclipse_Foundation\\.", "", import_data$name)

data <- merge(import_data,compress_data,by="name", all=TRUE)
#data <- na.omit(data)
data <- data[with(data, order(-FullSizeSum)),]
#data <- data[data$X1_cuCount>0,]
data <- head(data, 5)

rebind_5 = c(1,6,2,7,3,8,4,9,5,10)
rebind_10 = c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)

rebind = rebind_5

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
  bar_names <- sub("", "", data$name)
  bar_names <- lapply(bar_names, shorten)
  
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
plotCompressData("NamedElement-only Matcher", "GB", data$FullSizeSum*G, data$UCSizeSum*G, data$DeltaSizeNamedElementSum*G)
plotCompressData("Meta Class Matcher", "GB", data$FullSizeSum*G, data$UCSizeSum*G, data$DeltaSizeMetaClassSum*G)
plotCompressData("Lines", "MLines", data$FullLineCountSum*M, data$UCLineCountSum*M, (data$AddedLinesSum+data$RemovedLinesSum)*M)

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

parse = data$X4_refreshTimeSum+data$X5_importTimeSum-data$X6_writeTimeSum/1000
cMC = data$CompressETMetaClassSum/1000
cNM = data$CompressETNamedElementSum/1000
pMC = data$PatchETMetaClassSum/1000
pNM = data$PatchETNamedElementSum/1000

bar_data <- cbind(rbind(parse, cMC, 0), rbind(0, 0, pMC))
bar_data <- bar_data[,rebind]
bar_names <- sub("", "", data$name)
bar_names <- lapply(bar_names, shorten)
bar_lengend <- c("parse", "compress", "decompress")
barplot(
  bar_data/3600000,
  names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
  main="Execution time for example projects",
  ylab="(ms)",las=2,
  legend = bar_lengend,
  col = rainbow(4, s = 0.65),
  args.legend = list(x = "topright", cex = 1),
  space=c(.75,0)
)

bpDf = data.frame(parse, cNM, cMC, pNM, pMC)
boxplot(
  bpDf/3600000000,
  #names = c("uncompressed", "n.e. only", "meta class", "line diff"),
  outline = FALSE,
  #col = c(cols[2], cols[3], cols[3], cols[3]),
  main="Avg. execution times",
  ylab="execution time (hours)"
) 

# plotRatio = function(bar_title, unit, full, uc, delta) {
#   bar_data <- cbind(rbind(full, 0, 0), rbind(0, uc, delta))
#   bar_data <- bar_data[,rebind]
#   bar_names <- sub("", "", data$name)
#   bar_names <- lapply(bar_names, shorten)
#   
#   bar_lengend <- c("all", "uncompressed", "compressed")
#   barplot(
#     bar_data,
#     names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
#     main=bar_title,
#     ylab=unit,las=2,
#     legend = bar_lengend,
#     col = rainbow(3, s = 0.65),
#     args.legend = list(x = "topright", cex = 1),
#     space=c(.75,0))
# }


# boxRatio = function(title, legend, onePart, oneFull, twoPart, twoFull) {
#   one = onePart / oneFull
#   two = twoPart / twoFull
#   bar_data <- cbind(rbind(one, 0), rbind(0, two))
#   bar_data <- bar_data[,rebind]
#   bar_names = sub("", "", data$name)
#   barplot(
#     bar_data*100,
#     names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
#     main=title,
#     ylab="in %",las=2,
#     legend = legend,
#     col = rainbow(2, s = 0.65),
#     args.legend = list(x = "topright", cex = 1),
#     space=c(.75,0))  
# }
# 
# boxRatio("Line vs Size Ratio", c("Line", "Size"), 
#             data$UCLineCountSum + data$AddedLinesSum, data$FullLineCountSum,
#             data$UCSizeSum + data$DeltaSizeSum, data$FullSizeSum)
# boxRatio("Matched Lines vs Matched Objects Ratio", c("Lines", "Objects"), 
#           data$MatchedLineCountSum, data$FullLineCountSum,
#           data$MatchedObjectsCountSum, data$FullObjectCountSum)
dev.off()
