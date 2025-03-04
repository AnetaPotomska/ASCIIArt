package asciiArtApp.internalModules.filters.image.greyscale.flip.axis

import asciiArtApp.models.images.GreyscaleImage

class FlipYAxisFilter extends FlipAxisFilter {
  // go to half of image (from left to right) and in the meantime swap pixels (on whole height) for pixels on the other side of the image (left part for right part)
  override def exchangePixelsManipulator(newImage: GreyscaleImage, oldImage: GreyscaleImage, height: Int, width: Int): Unit = {
    val halfWidth = (width / 2) + 1
    for (h <- 0 until height) {
      for (w <- 0 until halfWidth) {
        newImage.setItemOnPos(h, w, oldImage.getItemOnPos(h, width - w - 1))
        newImage.setItemOnPos(h, width - w - 1, oldImage.getItemOnPos(h, w))
      }
    }
  }
}
