setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data/")
experiment_id = "16-03-19-testdrive-2"
data_file <- paste(c(experiment_id, "/data/importdata.csv"), collapse="")
import_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")
data_file <- paste(c(experiment_id, "/data/metrics.csv"), collapse="")
metrics_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")
colnames(metrics_data)[which(names(metrics_data) == "name")] <- "X1_name"
import_data$X1_name <- sub("Eclipse_Foundation\\.", "", import_data$X1_name)
import_data <- merge(import_data,metrics_data,by="X1_name", all=TRUE)

import_data <- import_data[with(import_data, order(-X1_dbSize)),]
import_data <- head(import_data, 10)

# execution times
checkout = import_data$X3_checkoutTimeSum
save = import_data$X6_writeTimeSum*(1+2)/1000
parse = import_data$X4_refreshTimeSum+import_data$X5_importTimeSum - save
traverse = import_data$RevUDFETN + import_data$RevComputeSSETSum

bar_data <- cbind(rbind(checkout, parse, save, 0),rbind(0,0,0,traverse))
bar_data <- bar_data[,c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)]

bar_names <- sub("^git.eclipse.org-([^-]*-)+","",import_data$X1_name,perl=TRUE)
bar_names <- sub("\\.git", "", bar_names)
bar_names <- sub("Eclipse_Foundation\\.", "", bar_names)
bar_names <- sub("eclipse\\.", "", bar_names)
bar_lengend <- sub("X[0-9]_","", c("checkout", "parse", "save", "traverse"))
barplot(
  bar_data/3600000000,
  names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
  main="Import/Traversal Execution Times",
  ylab="in hours",las=2,
  legend = bar_lengend,
  col = rainbow(6, s = 0.65),
  args.legend = list(x = "topright", cex = 1),
  space=c(.75,0))