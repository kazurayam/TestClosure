import java.awt.Dimension
import java.awt.Point

import com.kazurayam.ks.TileManager

TileManager tm = new TileManager(4, new Dimension(1000, 800))

Point loc0 = tm.getLocationOf(0)
Dimension dim0 = tm.getDimensionOf(0)
assert loc0.x == 10
assert loc0.y == 10
assert dim0.width == 800
assert dim0.height == 600

Point loc1 = tm.getLocationOf(1)
Dimension dim1 = tm.getDimensionOf(1)
assert loc1.x == 10
assert loc1.y == 10
assert dim1.width == 800
assert dim1.height == 600
