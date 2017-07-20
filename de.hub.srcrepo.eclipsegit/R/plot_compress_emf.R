setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")

import_data <- read.csv(file="16-04-06-big-summary/data/importdata.csv", head=TRUE, sep=",",comment.char="#")
metrics_data <- read.csv(file="16-04-06-big-summary/data/metrics.csv", head=TRUE, sep=",",comment.char="#")
compress_data <- read.csv(file="16-04-19-emf-compare-head2/data/compress.csv", head=TRUE, sep=",",comment.char="#")
compress_data2 <- read.csv(file="summary-16-04-18-compress/compress_fixed.csv", head=TRUE, sep=",",comment.char="#")

colnames(compress_data2)[which(names(compress_data2) == "FullObjectCountSum")] <- "FullObjectCountMetaClassSum"
colnames(compress_data2)[which(names(compress_data2) == "UCObjectCountSum")] <- "UCObjectCountMetaClassSum"
colnames(compress_data2)[which(names(compress_data2) == "FullSizeSum")] <- "FullSizeMetaClassSum"
colnames(compress_data2)[which(names(compress_data2) == "UCSizeSum")] <- "UCSizeMetaClassSum"
compress_data2 <- compress_data2[c("name", "CompressETMetaClassSum", "CompressETMetaClassN", "DeltaSizeMetaClassSum", "MatchedObjectsCountMetaClassSum","FullObjectCountMetaClassSum","UCObjectCountMetaClassSum","FullSizeMetaClassSum","UCSizeMetaClassSum")]

import_data$X1_name <- sub("Eclipse_Foundation\\.", "", import_data$X1_name)
colnames(import_data)[which(names(import_data) == "X1_name")] <- "name"

data <- merge(import_data,metrics_data,by="name", all=TRUE)
data <- merge(data,compress_data,by="name", all=TRUE)
data <- merge(data,compress_data2,by="name", all=TRUE)

data <- na.omit(data)
data <- data[with(data, order(-X1_cuCount)),]
data <- data[data$X1_cuCount>0,]
data <- data[data$CompressETSum>1000000000,]

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

colnames(data)[which(names(data) == "X1_name")] <- "name"

data <- data[with(data, order(-AddedLinesSum)),]

G=1/1000000000
M=1/1000000
attach(mtcars)

setwd("/Users/markus/Documents/Forschung/Paper/papers/BigMDE_2016/sources/figures/")
pdf("compress-emf.pdf", width=7, height=3)
layout(matrix(c(2,1,3), 1, 3, byrow = TRUE))

d = data
lineSize = d$FullSizeSum/d$FullLineCountSum
rUC = d$UCSizeSum/d$FullSizeSum
#rDNM = d$DeltaSizeNamedElementSum/d$FullSizeSum
#rDMC = d$DeltaSizeMetaClassSum/d$FullSizeSum
rMC = d$DeltaSizeMetaClassSum/(d$FullSizeMetaClassSum-d$UCSizeMetaClassSum)
rEC = d$DeltaSizeSum/(d$FullSizeSum-d$UCSizeSum)
rL = (d$AddedLinesSum*lineSize + d$RemovedLinesSum*2)/(d$FullLineCountSum*lineSize-d$UCLineCountSum*lineSize)

bpDf = data.frame(rMC, rEC, rL)
cols = rainbow(4, s=1)
boxplot(
  bpDf*100,
  names = c("signature", "similarity", "lines"),
  outline = FALSE,
  col = c(cols[3], cols[2], cols[4]),
  main="Comparison model size", ylime=c(0,100),
  ylab="(%)"
) 

d = data
rUC = d$UCSizeSum/d$FullSizeSum
#rDNM = d$DeltaSizeNamedElementSum/d$FullSizeSum
#rDMC = d$DeltaSizeMetaClassSum/d$FullSizeSum
rMC = d$MatchedObjectsCountMetaClassSum/(d$FullObjectCountMetaClassSum-d$UCObjectCountMetaClassSum)
rEC = d$MatchedObjectsCountSum/(d$FullObjectCountSum-d$UCObjectCountSum)
rL = (d$FullLineCountSum-d$UCLineCountSum-d$RemovedLinesSum-d$AddedLinesSum)/(d$FullLineCountSum-d$UCLineCountSum)

bpDf = data.frame(rMC, rEC, rL)
boxplot(
  bpDf*100,
  names = c("signature", "similarity", "lines"),
  outline = FALSE,
  col = c(cols[3], cols[3], cols[4]),
  main="Number of matches", ylime=c(0,100),
  ylab="(%)"
) 

#dev.off()

#pdf("compress-emf-et.pdf", width=9, height=4.5)
#layout(matrix(c(1,2), 1, 2, byrow = TRUE))

ns = 1000*1000
ys = 1000
parse = pmax(data$X4_refreshTimeSum+data$X5_importTimeSum-data$X6_writeTimeSum,0)/(data$X1_cuCount*ys)
cEC = data$CompressETSum/(data$CompressETN*ns)
cMC = data$CompressETMetaClassSum/(data$CompressETMetaClassN*ns)+1

# bpDf = data.frame(cMC, cEC, parse)
# boxplot(
#   bpDf,
#   names = c("signature", "similarity", "parse"),
#   outline = FALSE,
#   col = c(cols[3], cols[3], cols[4]),
#   main="Avg. execution times",
#   ylab="avg. time per compilation unit (ms)"
# ) 

bpDf = data.frame(cMC, cEC, parse)
boxplot(
  bpDf,
  names = c("signature", "similarity", "parse"),
  outline = FALSE,
  col = c(cols[3], cols[3], cols[4]),
  main="Avg. execution times (log)",
  ylab="avg. time per compilation unit (ms)",
  log = "y", ylim = c(1,1200)
) 
dev.off()
