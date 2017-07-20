setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")

missing <- function(experiment_id) {
  data_file <- paste(c(experiment_id, "/data/importdata.csv"), collapse="")
  import_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")
  data_file <- paste(c(experiment_id, "/data/metrics.csv"), collapse="")
  metrics_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")
  colnames(metrics_data)[which(names(metrics_data) == "name")] <- "X1_name"
  import_data$X1_name <- sub("Eclipse_Foundation\\.", "", import_data$X1_name)
  data <- merge(import_data,metrics_data,by="X1_name", all=TRUE)
  missing <- subset(data,is.na(data$FragUnloadETSum))
  missing <- subset(missing, missing$X1_cuCount > 0)$X1_name
  paste(missing, collapse=" ")
}

missing("16-04-06-big-summary")
