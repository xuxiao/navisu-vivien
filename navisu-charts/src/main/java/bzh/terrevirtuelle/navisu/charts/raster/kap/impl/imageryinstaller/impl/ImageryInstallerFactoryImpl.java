/**
 * This file is part of NaVisu.
 *
 * NaVisu is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * NaVisu is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * NaVisu. If not, see <http://www.gnu.org/licenses/>.
 */
package bzh.terrevirtuelle.navisu.charts.raster.kap.impl.imageryinstaller.impl;

import bzh.terrevirtuelle.navisu.charts.raster.kap.impl.imageryinstaller.ImageryInstaller;
import bzh.terrevirtuelle.navisu.charts.raster.kap.impl.imageryinstaller.ImageryInstallerFactory;

/**
 *
 * @author Thibault
 */
public class ImageryInstallerFactoryImpl implements ImageryInstallerFactory {

    @Override
    public ImageryInstaller newImageryInstaller() {
        return new ImageryInstallerImpl();
    }
}
