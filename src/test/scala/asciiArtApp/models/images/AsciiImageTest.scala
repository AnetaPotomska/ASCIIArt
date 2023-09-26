package asciiArtApp.models.images

import asciiArtApp.models.grids.AsciiGrid
import asciiArtApp.models.pixels.AsciiPixel
import org.scalatest.FunSuite

class AsciiImageTest extends FunSuite {
  val squareGridImage: AsciiImage = AsciiImage(AsciiGrid(Array(Array(
    AsciiPixel('a'),
    AsciiPixel('b')),
    Array(AsciiPixel('c'),
      AsciiPixel('d')))))

  // ------------------------------------------------------------
  // HEIGHT

  test("Get height") {
    val height = squareGridImage.getHeight
    assert(height == 2)
  }

  // ------------------------------------------------------------
  // WIDTH

  test("Get width") {
    val width = squareGridImage.getWidth
    assert(width == 2)
  }

  // ------------------------------------------------------------
  // SET ITEM ON POS

  test("Set item o pos (in bounds)") {
    val height = 2
    val width = 2
    val newsquareGridImage: AsciiGrid = AsciiGrid(Array.ofDim[AsciiPixel](height, width))

    newsquareGridImage.setItemOnPos(0, 0, AsciiPixel('a'))
    newsquareGridImage.setItemOnPos(0, 1, AsciiPixel('b'))
    newsquareGridImage.setItemOnPos(1, 0, AsciiPixel('c'))
    newsquareGridImage.setItemOnPos(1, 1, AsciiPixel('d'))

    assert(newsquareGridImage.getItemOnPos(0, 0) == AsciiPixel('a'))
    assert(newsquareGridImage.getItemOnPos(0, 1) == AsciiPixel('b'))
    assert(newsquareGridImage.getItemOnPos(1, 0) == AsciiPixel('c'))
    assert(newsquareGridImage.getItemOnPos(1, 1) == AsciiPixel('d'))
  }

  test("Set item o pos (out of bounds)") {
    val height = 1
    val width = 1
    val newsquareGridImage: AsciiGrid = AsciiGrid(Array.ofDim[AsciiPixel](height, width))

    val caught =
      intercept[Exception] {
        newsquareGridImage.setItemOnPos(0, 1, AsciiPixel('b'))
      }
    assert(caught.getMessage == "Invalid coordinates")
  }

  // ------------------------------------------------------------
  // GET ITEM ON POS

  test("Get item o pos (in bounds)") {
    assert(squareGridImage.getItemOnPos(0, 0) == AsciiPixel('a'))
    assert(squareGridImage.getItemOnPos(0, 1) == AsciiPixel('b'))
    assert(squareGridImage.getItemOnPos(1, 0) == AsciiPixel('c'))
    assert(squareGridImage.getItemOnPos(1, 1) == AsciiPixel('d'))
  }

  test("Get item o pos (out of bounds)") {
    val caught =
      intercept[Exception] {
        squareGridImage.getItemOnPos(0, 2) == AsciiPixel('a')
      }
    assert(caught.getMessage == "Invalid coordinates")
  }

  // ------------------------------------------------------------
  // CHECK COORDINATION

  test("Negative coordination") {
    assert(!squareGridImage.checkCoordination(-1, -1))
    assert(!squareGridImage.checkCoordination(-1, 0))
    assert(!squareGridImage.checkCoordination(0, -1))
  }

  test("X larger than height") {
    assert(!squareGridImage.checkCoordination(2, 0))
    assert(!squareGridImage.checkCoordination(5, 0))
    assert(!squareGridImage.checkCoordination(9, 0))
  }

  test("Y larger than width") {
    assert(!squareGridImage.checkCoordination(0, 2))
    assert(!squareGridImage.checkCoordination(0, 5))
    assert(!squareGridImage.checkCoordination(0, 9))
  }

  // ------------------------------------------------------------
  // ITERATOR

  test("Doesn't have next") {
    val height = 0
    val width = 0
    val newsquareGridImage: AsciiGrid = AsciiGrid(Array.ofDim[AsciiPixel](height, width))

    assert(!newsquareGridImage.iterator.hasNext)
  }

  test("Go through grid with iterator") {
    val iterator = squareGridImage.iterator
    var coord = iterator.next
    assert(coord._1 == 0 && coord._2 == 0)
    coord = iterator.next
    assert(coord._1 == 0 && coord._2 == 1)
    coord = iterator.next
    assert(coord._1 == 1 && coord._2 == 0)
    coord = iterator.next
    assert(coord._1 == 1 && coord._2 == 1)
    val caught =
      intercept[Exception] {
        coord = iterator.next
      }
    assert(caught.getMessage == "No more elements in the grid")
  }
}
