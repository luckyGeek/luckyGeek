/**********************************
 *ConversionTypes.java
 *Part of the project "luckyGeek" from
 *ctvoigt (Christian Voigt), chripo2701  2011.
 *
 *
 *Email: luckygeek@verpeil.de
 *
 *
 *
 **********************************
 *
 *Enumeration of all possible image to pdf converts.
 **********************************
 *
 *This program is free software; you can redistribute it
 *and/or modify it under the terms of the GNU General
 *Public License as published by the Free Software
 *Foundation; either version 2 of the License, or (at your
 *option) any later version.
 *This program is distributed in the hope that it will be
 *useful, but WITHOUT ANY WARRANTY; without even the implied
 *warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *PURPOSE. See the GNU General Public License for more details.
 *You should have received a copy of the GNU General Public
 *License along with this program; if not, write to the Free
 *Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *MA 02111-1307, USA.
 */
package de.verpeil.luckygeek;

import java.util.Locale;
import java.util.logging.Logger;

/**
 *Containes types for conversion.
 */
enum ConversionTypes {
    PDFBOX, 
    IMAGEMAGICK {
        @Override
        public Converter createConverter() {
            return new ConverterDecorator(new ImagemagickConverter());
        }
    };

    private static final Logger LOG = Logger.getLogger(ConversionTypes.class
            .getCanonicalName());

    static ConversionTypes parse(String value) {
        ConversionTypes result = PDFBOX;
        if (value == null || value.isEmpty()) {
            return result;
        }

        try {
            result = ConversionTypes.valueOf(value.toUpperCase(Locale.getDefault()));
        } catch (Exception e) {
            LOG.warning(String.format("Can not parse value '%s'. Message: %s.",
                    value, e.getMessage()));
        }
        return result;
    }

    Converter createConverter() {
        return new ConverterDecorator(new PdfBoxConverter());
    }
}
