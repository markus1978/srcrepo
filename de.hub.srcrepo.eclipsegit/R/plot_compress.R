setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")
experiment_id = "16-03-21-testdrive-2-compress"
data_file <- paste(c(experiment_id, "/data/compress.csv"), collapse="")
import_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")

import_data <- import_data[with(import_data, order(-FullSizeSum)),]
import_data <- head(import_data, 10)

plotCompressData = function(name, unit, full, uc, delta) {
  bar_data <- cbind(rbind(full, 0, 0), rbind(0, uc, delta))
  bar_data <- bar_data[,c(1,11,2,12,3,13,4,14,5,15,6,16,7,17,8,18,9,19,10,20)]
  bar_names = sub("", "", import_data$name)
  
  bar_title <- paste(c("Full vs Compressed ", name))
  
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
plotCompressData("Size", "GB", import_data$FullSizeSum*G, import_data$UCSizeSum*G, import_data$DeltaSizeMetaClassSum*G)
plotCompressData("Size", "GB", import_data$FullSizeSum*G, import_data$UCSizeSum*G, import_data$DeltaSizeHeuristicsSum*G)
plotCompressData("Size", "GB", import_data$FullSizeSum*G, import_data$UCSizeSum*G, import_data$DeltaSizeNamedElementSum*G)
plotCompressData("Lines", "MLOC", import_data$FullLineCountSum*M, import_data$UCLineCountSum*M, import_data$AddedLinesSum*M)

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

