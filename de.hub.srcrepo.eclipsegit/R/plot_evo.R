setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")
experiment_id = "16-03-30-big-2"
data_file <- paste(c(experiment_id, "/data/metrics_cdt-data.csv"), collapse="")
import_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")

x <- as.Date(import_data$time, format="%d-%m-%Y")
hal <- import_data$wmchal / 1000000
loc <- import_data$loc

cor(hal, loc)

par(pch=22, col="black") # plotting symbol and color 
par(mfrow=c(1,1)) # all plots on one page 
opts = c("p","l","o","b","c","s","S","h") 
i = 1

library(hexbin)
df = data.frame(x,hal)
df = hexbin(df,xbins=300)
plot(df,
     xlab="time (years)", 
     ylab=expression(WMC~with~Halstead~length~(x~10^{6})), 
     main="WMC weighted with Halstead length (CDT)."
)

library(gplots)
xn = as.numeric(x)
df = data.frame(xn,hal)
h2d = hist2d(df)

x = x[seq(0,length(x), 20)]
hal = hal[seq(0,length(hal), 20)]

setwd("/Users/markus/Documents/Forschung/Paper/papers/Models2016/sources/figures/")
pdf("evo_example.pdf", width=5.5, height=5.5)
heading = paste("type=",opts[i]) 
plot(x, hal, type="n",xlab="time (years)", 
     ylab=expression(WMC~with~Halstead~length~(x~10^{6})), 
     main="WMC weighted with Halstead length (CDT)."
) 
lines(x, hal, type=opts[i], cex=0.5)
dev.off()