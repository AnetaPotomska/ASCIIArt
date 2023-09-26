package asciiArtApp.models.images

import asciiArtApp.models.grids.GreyscaleGrid
import asciiArtApp.models.pixels.GreyscalePixel
import org.scalatest.FunSuite

class GreyscaleImageTest extends FunSuite {
  val squareGridImage: GreyscaleImage = GreyscaleImage(GreyscaleGrid(Array(Array(
    GreyscalePixel(0),
    GreyscalePixel(1)),
    Array(GreyscalePixel(2),
      GreyscalePixel(3)))))

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
    val newsquareGridImage: GreyscaleGrid = GreyscaleGrid(Array.ofDim[GreyscalePixel](height, width))

    newsquareGridImage.setItemOnPos(0, 0, GreyscalePixel(0))
    newsquareGridImage.setItemOnPos(0, 1, GreyscalePixel(1))
    newsquareGridImage.setItemOnPos(1, 0, GreyscalePixel(2))
    newsquareGridImage.setItemOnPos(1, 1, GreyscalePixel(3))

    assert(newsquareGridImage.getItemOnPos(0, 0) == GreyscalePixel(0))
    assert(newsquareGridImage.getItemOnPos(0, 1) == GreyscalePixel(1))
    assert(newsquareGridImage.getItemOnPos(1, 0) == GreyscalePixel(2))
    assert(newsquareGridImage.getItemOnPos(1, 1) == GreyscalePixel(3))
  }

  test("Set item o pos (out of bounds)") {
    val height = 1
    val width = 1
    val newsquareGridImage: GreyscaleGrid = GreyscaleGrid(Array.ofDim[GreyscalePixel](height, width))

    val caught =
      intercept[Exception] {
        newsquareGridImage.setItemOnPos(0, 1, GreyscalePixel(1))
      }
    assert(caught.getMessage == "Invalid coordinates")
  }

  // ------------------------------------------------------------
  // GET ITEM ON POS

  test("Get item o pos (in bounds)") {
    assert(squareGridImage.getItemOnPos(0, 0) == GreyscalePixel(0))
    assert(squareGridImage.getItemOnPos(0, 1) == GreyscalePixel(1))
    assert(squareGridImage.getItemOnPos(1, 0) == GreyscalePixel(2))
    assert(squareGridImage.getItemOnPos(1, 1) == GreyscalePixel(3))
  }

  test("Get item o pos (out of bounds)") {
    val caught =
      intercept[Exception] {
        squareGridImage.getItemOnPos(0, 2) == GreyscalePixel(0)
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
    val newsquareGridImage: GreyscaleGrid = GreyscaleGrid(Array.ofDim[GreyscalePixel](height, width))

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
