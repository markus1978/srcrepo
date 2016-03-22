setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")
experiment_id = "16-03-21-testdrive-2-compress"
data_file <- paste(c(experiment_id, "/data/compress.csv"), collapse="")
import_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")

import_data <- import_data[with(import_data, order(-FullSizeSum)),]
import_data <- head(import_data, 5)

rebind_5 = c(1,6,2,7,3,8,4,9,5,10)
rebind_10 = c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)

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
  bar_data <- bar_data[,rebind_5]
  bar_names <- sub("", "", import_data$name)
  bar_names <- lapply(bar_names, shorten)
  
  bar_lengend <- c("Full", "UC", "Delta")
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
layout(matrix(c(1,2,3,4,5,6), 2, 3, byrow = TRUE))
plotCompressData("Size - NamedElement-only Matcher", "GB", import_data$FullSizeSum*G, import_data$UCSizeSum*G, import_data$DeltaSizeNamedElementSum*G)
plotCompressData("Size - Meta Class Matcher", "GB", import_data$FullSizeSum*G, import_data$UCSizeSum*G, import_data$DeltaSizeMetaClassSum*G)
plotCompressData("Lines", "MLines", import_data$FullLineCountSum*M, import_data$UCLineCountSum*M, (import_data$AddedLinesSum+import_data$RemovedLinesSum)*M)

plotCompressData("All vs Matched - NamedElement-only Matcher", "MObjects", import_data$FullObjectCountSum*M, import_data$UCObjectCountSum*M, import_data$MatchedObjectsCountNamedElementSum*M)
plotCompressData("All vs Matched - Meta Class Matcher", "MObjects", import_data$FullObjectCountSum*M, import_data$UCObjectCountSum*M, import_data$MatchedObjectsCountMetaClassSum*M)
plotCompressData("All vs Matched Lines", "MLines", import_data$FullLineCountSum*M, import_data$UCLineCountSum*M, import_data$MatchedLineCountSum*M)

plotRatio = function(title, legend, onePart, oneFull, twoPart, twoFull) {
  one = onePart / oneFull
  two = twoPart / twoFull
  bar_data <- cbind(rbind(one, 0), rbind(0, two))
  bar_data <- bar_data[,c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)]
  bar_names = sub("", "", import_data$name)
  barplot(
    bar_data*100,
    names = rbind(bar_names, vector(mode="character",length=length(bar_names))),
    main=title,
    ylab="in %",las=2,
    legend = legend,
    col = rainbow(2, s = 0.65),
    args.legend = list(x = "topright", cex = 1),
    space=c(.75,0))  
}

plotRatio("Line vs Size Ratio", c("Line", "Size"), 
            import_data$UCLineCountSum + import_data$AddedLinesSum, import_data$FullLineCountSum,
            import_data$UCSizeSum + import_data$DeltaSizeSum, import_data$FullSizeSum)
plotRatio("Matched Lines vs Matched Objects Ratio", c("Lines", "Objects"), 
          import_data$MatchedLineCountSum, import_data$FullLineCountSum,
          import_data$MatchedObjectsCountSum, import_data$FullObjectCountSum)

