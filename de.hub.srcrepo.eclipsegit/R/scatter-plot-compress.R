library(ggplot2)
library(grid)
library(scales)

multiplot <- function(..., plotlist=NULL, file, cols=1, layout=NULL) {
  # Make a list from the ... arguments and plotlist
  plots <- c(list(...), plotlist)
  
  numPlots = length(plots)
  
  # If layout is NULL, then use 'cols' to determine layout
  if (is.null(layout)) {
    # Make the panel
    # ncol: Number of columns of plots
    # nrow: Number of rows needed, calculated from # of cols
    layout <- matrix(seq(1, cols * ceiling(numPlots/cols)),
                     ncol = cols, nrow = ceiling(numPlots/cols))
  }
  
  if (numPlots==1) {
    print(plots[[1]])
    
  } else {
    # Set up the page
    grid.newpage()
    pushViewport(viewport(layout = grid.layout(nrow(layout), ncol(layout))))
    
    # Make each plot, in the correct location
    for (i in 1:numPlots) {
      # Get the i,j matrix positions of the regions that contain this subplot
      matchidx <- as.data.frame(which(layout == i, arr.ind = TRUE))
      
      print(plots[[i]], vp = viewport(layout.pos.row = matchidx$row,
                                      layout.pos.col = matchidx$col))
    }
  }
}

setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")

emfCompareScatterData <- read.csv(file="summary-16-04-18-compress/scatter_emfCompare.csv", head=TRUE, sep=",",comment.char="#")
metaClassScatterData <- read.csv(file="summary-16-04-18-compress/scatter_metaClass.csv", head=TRUE, sep=",",comment.char="#")
namedElementScatterData <- read.csv(file="summary-16-04-18-compress/scatter_namedElement.csv", head=TRUE, sep=",",comment.char="#")

metaClassScatterData <- metaClassScatterData[c("time", "count")]
namedElementScatterData <- namedElementScatterData[c("time", "count")]

metaClassScatterData <- metaClassScatterData[metaClassScatterData[,"count"]<=20000,]
namedElementScatterData <- namedElementScatterData[namedElementScatterData[,"count"]<=20000,]
emfCompareScatterData <- emfCompareScatterData[emfCompareScatterData[,"count"]<=20000,]

setwd("/Users/markus/Desktop/")

plotScatterData <- function(theData, limits) {
  # generare random emfCompareScatterData, swap this for yours :-)!
  n <- 360000
  theData <- head(theData, n)
  x <- theData$count
  y <- theData$time/1000000000
  DF <- data.frame(x,y)
  
  # Calculate 2d density over a grid
  library(MASS)
  dens <- kde2d(x,y)
  
  # create a new data frame of that 2d density grid
  # (needs checking that I haven't stuffed up the order here of z?)
  gr <- data.frame(with(dens, expand.grid(x,y)), as.vector(dens$z))
  names(gr) <- c("xgr", "ygr", "zgr")
  
  # Fit a model
  mod <- loess(zgr~xgr*ygr, data=gr)
  
  # Apply the model to the original data to estimate density at that point
  DF$pointdens <- predict(mod, newdata=data.frame(xgr=x, ygr=y))
  
  # Draw plot
  p <- ggplot(DF,aes(x=x,y=y),log="y") + stat_bin2d(bins=200) + limits + xlim(0,20000)+ scale_fill_gradient(low = "blue", high = "red", trans='log')
  update_labels(p, list(x="CU size (#objects)",y="time (s)"))
  p
}

layout(matrix(c(1,2,3), 3, 1, byrow = TRUE))

o1 <- plotScatterData(emfCompareScatterData, ylim(0,30))
o2 <- plotScatterData(namedElementScatterData, ylim(0,30))
o3 <- plotScatterData(metaClassScatterData, ylim(0,30))


p1 <- plotScatterData(emfCompareScatterData, ylim(0,2))
p2 <- plotScatterData(namedElementScatterData, ylim(0,2))
p3 <- plotScatterData(metaClassScatterData, ylim(0,2))

setwd("/Users/markus/Desktop/")
pdf("scatter.pdf", width=15, height=7)
multiplot(o1, o2, o3, p1, p2, p3, cols=3, layout=matrix(c(1,2,3,4,5,6), nrow=2, byrow=TRUE))
dev.off()  
