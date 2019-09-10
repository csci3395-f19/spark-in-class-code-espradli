package basics
import swiftvis2.plotting._
import swiftvis2.plotting.styles.ScatterStyle
import swiftvis2.plotting.renderer.SwingRenderer
import swiftvis2.plotting.ColorGradient

case class TempRow(day: Int, doy: Int, month: Int, year: Int, precip: Double, 
  tave: Double, tmax: Double, tmin: Double)

object SATemps{
    def parseLine(line: String): TempRow ={
    val p = line.split(",")
    TempRow(p(0).toInt, p(1).toInt, p(2).toInt, p(4).toInt, p(5).toDouble, 
      p(6).toDouble, p(7).toDouble, p(8).toDouble)
  }

  def main(args: Array[String]): Unit = {
    val source = scala.io.Source.fromFile("/users/mlewis/CSCI3395-F19/InClassBD/data/SanAntonioTemps.csv")
    val lines = source.getLines()
    val data = lines.drop(2).map(parseLine).toArray

    val highestHight = data.maxBy(_.precip)
    println(highestHight.month + "/"+highestHight.day +"/" +highestHight.year)

    val bigPrecip = data.count(_.precip > 1)

    val sizes = data.map(_.precip *2 + 2)
    val cg = ColorGradient(1946.0 -> RedARGB, 1975.0 -> BlueARGB, 2014.0 -> GreenARGB)
    val tempByDayPlaot = Plot.simple(ScatterStyle(data.map(_.doy), data.map(_.tave), symbolWidth = sizes, symbolHeight = sizes, colors = cg(data.map(_.year))), 
      "SA Temps","Day of Year", "Temp")
    SwingRenderer(tempByDayPlaot, 800, 800, true)
  }
}