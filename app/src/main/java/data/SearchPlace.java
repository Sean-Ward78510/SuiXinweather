package data;

import org.litepal.crud.LitePalSupport;

import java.util.Objects;

public class SearchPlace extends LitePalSupport {
    public String placeName;
    public String placeId;
    public String amd2;
    public String adm1;
    public SearchPlace() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchPlace that = (SearchPlace) o;
        return Objects.equals(placeName, that.placeName) && Objects.equals(placeId, that.placeId) && Objects.equals(amd2, that.amd2) && Objects.equals(adm1, that.adm1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeName, placeId, amd2, adm1);
    }
}
