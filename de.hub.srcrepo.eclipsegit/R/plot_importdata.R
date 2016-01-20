setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")
import_data <- read.csv(file="importdata-new-clean.csv", head=TRUE, sep=",")

import_data <- import_data[with(import_data, order(-X2_dbSize)),]
import_data <- head(import_data, 10)

# execution times
checkout = import_data$X3_checkoutTimeSum
save = import_data$X6_writeTimeSum*(1+2)/1000
parse = import_data$X4_refreshTimeSum+import_data$X5_importTimeSum - save

bar_data <- cbind(rbind(checkout, parse, save, 0),rbind(0,0,0,import_data$X8_traverseTimeSum/1000))
bar_data <- bar_data[,c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)]

bar_names <- sub("^git.eclipse.org-([^-]*-)+","",import_data$X1_name,perl=TRUE)
bar_names <- sub("\\.git", "", bar_names)
bar_names <- sub("Eclipse_Foundation\\.", "", bar_names)
bar_names <- sub("eclipse\\.", "", bar_names)
bar_lengend <- sub("X[0-9]_","", c("checkout", "parse", "save", "traverse"))
barplot(
  bar_data/3600000,
  names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
  main="Import/Traversal Execution Times",
  ylab="in hours",las=2,
  legend = bar_lengend,
  col = rainbow(6, s = 0.65),
  args.legend = list(x = "topright", cex = 1),
  space=c(.75,0))

# sizes besides
bar_data <- rbind(import_data$X2_gitSize/(1024*1024), import_data$X2_dbSize/(1024*1024*1024))
bar_lengend <- sub("X[0-9]_","", c("GIT size", "Model size"))
barplot(
  bar_data, beside=TRUE,
  names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
  main="GIT repository vs model size",
  las=2, ylab="in GB",
  legend = bar_lengend,
  col = rainbow(2, s = 0.65),
  args.legend = list(x = "topright", cex = 1))

# sizes relative
bar_data <- rbind(import_data$X2_dbSize/(1024*import_data$X2_gitSize))
bar_lengend <- sub("X[0-9]_","", c("one"))
barplot(
  bar_data,
  names = bar_names,
  main="Model size in multiples of GIT size",
  las=2,
  legend = bar_lengend,
  col = rainbow(2, s = 0.65),
  args.legend = list(x = "topright", cex = 1))

# element count
bar_data <- import_data$X2_elementCount/1000000
barplot(
  bar_data,
  names = bar_names,
  main="Number of million objects",
  las=2,
  col = rainbow(1, s = 0.65))

# element count vs model size
one = import_data$X2_dbSize/(1024*1024*1024)
two = import_data$X2_elementCount/(1000000)
rel = max(one)/350
twof = two*rel
bar_data <- rbind(one, twof)
barplot(bar_data, beside = TRUE,
        yaxt = "n", names.arg = bar_names, las=2)
axis(2, at = seq(0, 16, length.out = 9))

axis(4, at = seq(0, 350*rel, length.out = 8),
     labels = round(seq(0, 350, length.out = 8)))

