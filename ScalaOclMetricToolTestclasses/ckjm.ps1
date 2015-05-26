$sw = New-Object System.Diagnostics.Stopwatch
$Dir = 'C:\Users\Worm\Git_Workspace\Studienarbeit\ScalaOclMetricToolTestclasses\ScalaOclMetricToolTestclasses\ScalaOclMetricToolTestclasses\bin\testclasses'
$classes = Get-ChildItem -Path $Dir
$fc = ForEach( $File in $classes ) { $File.Fullname }

$sw.Start()
ForEach($item in $fc) {java -jar C:\Users\Worm\Programme\Ckjm_1.9\ckjm-1.9\build\ckjm-1.9.jar $item | Out-File C:\Users\Worm\Git_Workspace\Studienarbeit\ScalaOclMetricToolTestclasses\ScalaOclMetricToolTestclasses\ScalaOclMetricToolTestclasses\ckjm.txt -Append}
$sw.Stop()
$sw.Elapsed.ToString() | Out-File C:\Users\Worm\Git_Workspace\Studienarbeit\ScalaOclMetricToolTestclasses\ScalaOclMetricToolTestclasses\ScalaOclMetricToolTestclasses\ckjm.txt -Append