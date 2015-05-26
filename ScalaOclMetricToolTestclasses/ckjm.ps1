$baseDir = 'C:\Users\Sera\git\ScalaOclMetricToolTestclasses\ScalaOclMetricToolTestclasses'
#$baseDir = 'C:\Users\Worm\Git_Workspace\Studienarbeit\ScalaOclMetricToolTestclasses\ScalaOclMetricToolTestclasses\ScalaOclMetricToolTestclasses'

$sw = New-Object System.Diagnostics.Stopwatch
$classFileDir = "${baseDir}\bin\testclasses"
$classes = Get-ChildItem -Path $classFileDir
$fullPathToClassFiles = ForEach( $File in $classes ) { $File.Fullname }

$sw.Start()
ForEach($item in $fullPathToClassFiles) {
    java -jar 'C:/Program Files/ckjm-1.9/ckjm-1.9/build/ckjm-1.9.jar' $item | Out-File ${baseDir}\ckjm2.txt -Append
    #java -jar C:\Users\Worm\Programme\Ckjm_1.9\ckjm-1.9\build\ckjm-1.9.jar $item | Out-File ${baseDir}\ckjm.txt -Append
}
$sw.Stop()
$sw.Elapsed.ToString() | Out-File ${baseDir}\ckjm2.txt -Append