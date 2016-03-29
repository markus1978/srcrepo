setwd("/Users/markus/Documents/Projects/srcrepo-mars/07-jupiter/08-data")
experiment_id = "16-03-22-big"
data_file <- paste(c(experiment_id, "/data/metrics_cdo-data.csv"), collapse="")
import_data <- read.csv(file=data_file, head=TRUE, sep=",",comment.char="#")

x <- as.Date(import_data$time, format="%d-%m-%Y")
hal <- import_data$wmchal / 1000000
loc <- import_data$loc

cor(hal, loc)

par(pch=22, col="black") # plotting symbol and color 
par(mfrow=c(1,1)) # all plots on one page 
opts = c("p","l","o","b","c","s","S","h") 
i = 1

heading = paste("type=",opts[i]) 
plot(x, hal, type="n",xlab="time (years)", ylab="complexity (mHAL)", main="Complexity over project evolution (CDO).") 
lines(x, hal, type=opts[i])