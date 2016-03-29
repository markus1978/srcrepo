setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")
experiment_id = "16-03-22-big"
data_file <- paste(c(experiment_id, "/data/importdata.csv"), collapse="")
import_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")
data_file <- paste(c(experiment_id, "/data/metrics.csv"), collapse="")
metrics_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")
colnames(metrics_data)[which(names(metrics_data) == "name")] <- "X1_name"
import_data$X1_name <- sub("Eclipse_Foundation\\.", "", import_data$X1_name)
import_data <- merge(import_data,metrics_data,by="X1_name", all=TRUE)


import_data <- import_data[with(import_data, order(-X1_cuCount)),]
import_data <- head(import_data, 20)

import_data <- import_data[import_data$X1_cuCount>0,]

rel = import_data$X1_cuCount*1000

checkout = import_data$X3_checkoutTimeSum/rel
refresh = import_data$X4_refreshTimeSum/rel
parse = (import_data$X5_importTimeSum-import_data$X6_writeTimeSum)/rel
write = import_data$X6_writeTimeSum/rel
read = import_data$DataReadETSum/rel
snapshot = import_data$RevComputeSSETSum/rel
udf = import_data$RevUDFETSum/rel

bnames <- c("checkout", "refresh", "parse", "write", "read", "snapshot", "udf")
colors <- c("red", "red", "red", "red", "blue", "blue","blue")

layout(matrix(c(1,2), 1, 2, byrow = TRUE))
boxplot(
  checkout, refresh, parse, write, read, snapshot, udf, 
  col = colors,
  names=bnames, 
  main="Execution times.",
  ylab="avg time per revision (ms)"
)
boxplot(
  checkout, refresh, parse, write, read, snapshot, udf, 
  col = colors,
  names=bnames, 
  main="Execution times (log scale).", log = "y",
  ylab="avg time per revision (ms)"
)