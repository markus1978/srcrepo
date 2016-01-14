setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")
import_data <- read.csv(file="importdata-withproxies.csv", head=TRUE, sep=",")

import_data <- import_data[with(import_data, order(-X2_dbSize)),]
import_data <- head(import_data, 10)

bar_data <- cbind(rbind(import_data$X3_checkoutTimeSum,import_data$X4_refreshTimeSum, import_data$X5_importTimeSum, import_data$X6_writeTimeSum/1000, 0),rbind(0,0,0,0,import_data$X8_traverseTime))
bar_data <- bar_data[,c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)]

bar_names <- sub("^git.eclipse.org-([^-]*-)+","",import_data[,1],perl=TRUE)
bar_names <- sub("\\.git", "", bar_names)
bar_names <- sub("org\\.", "", bar_names)
bar_names <- sub("eclipse\\.", "", bar_names)
bar_lengend <- sub("X[0-9]_","", c("checkout", "refresh", "import", "db", "traverse"))
barplot(
  bar_data/3600000,
  names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
  main="Import/Traversal Execution Times",
  ylab="in hours",las=2,
  legend = bar_lengend,
  col = rainbow(6, s = 0.65),
  args.legend = list(x = "topright", cex = 1),
  space=c(.75,0))

bar_data <- rbind(import_data$X2_cuCount/import_data$X2_revCount, import_data$X2_elementCount/import_data$X2_cuCount/100)
bar_lengend <- sub("X[0-9]_","", c("CUs per revision", "100 elements per CU"))
barplot(
  bar_data, beside=TRUE,
  names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
  main="Relations between CUs, Revisions, and overall Elements",
  las=2,
  legend = bar_lengend,
  col = rainbow(2, s = 0.65),
  args.legend = list(x = "topright", cex = 1))

bar_data <- import_data$X2_elementCount/1000000
barplot(
  bar_data,
  names = bar_names,
  main="Number of Million Objects",
  las=2,
  col = rainbow(1, s = 0.65))