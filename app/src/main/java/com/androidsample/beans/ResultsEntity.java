package com.androidsample.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResultsEntity implements Parcelable {

    public static final Parcelable.Creator<ResultsEntity> CREATOR = new Parcelable.Creator<ResultsEntity>() {
        @Override
        public ResultsEntity createFromParcel(Parcel source) {
            return new ResultsEntity(source);
        }

        @Override
        public ResultsEntity[] newArray(int size) {
            return new ResultsEntity[size];
        }
    };
    /**
     * url : https://www.nytimes.com/2018/07/01/world/americas/mexico-election-andres-manuel-lopez-obrador.html
     * adx_keywords : Lopez Obrador, Andres Manuel;Pena Nieto, Enrique;Politics and Government;Elections;Corruption (Institutional);Mexico;Institutional Revolutionary Party (Mexico);Economic Conditions and Trends;National Action Party (Mexico);Latin America
     * column : null
     * section : World
     * byline : By AZAM AHMED and PAULINA VILLEGAS
     * type : Article
     * title : López Obrador, an Atypical Leftist, Wins Mexico Presidency in Landslide
     * abstract : Andrés Manuel López Obrador’s victory, fueled by a wave of voter anger, upended the political establishment, filling millions of Mexicans with hope — and the nation’s elites with trepidation.
     * published_date : 2018-07-01
     * source : The New York Times
     * id : 100000005985408
     * asset_id : 100000005985408
     * views : 1
     * des_facet : ["POLITICS AND GOVERNMENT","ELECTIONS","CORRUPTION (INSTITUTIONAL)","ECONOMIC CONDITIONS AND TRENDS"]
     * org_facet : ["INSTITUTIONAL REVOLUTIONARY PARTY (MEXICO)","NATIONAL ACTION PARTY (MEXICO)"]
     * per_facet : ["LOPEZ OBRADOR, ANDRES MANUEL","PENA NIETO, ENRIQUE"]
     * geo_facet : ["MEXICO","LATIN AMERICA"]
     * media : [{"type":"image","subtype":"photo","caption":"Andrés Manuel López Obrador campaigned on a narrative of social change, including increased pensions for the elderly, educational grants for Mexico\u2019s youth and additional support for farmers.","copyright":"Manuel Velasquez/Getty Images","approved_for_syndication":1,"media-metadata":[{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/02mexico-top-square320.jpg","format":"square320","height":320,"width":320},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/02mexico-top-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-articleInline.jpg","format":"Normal","height":127,"width":190},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-sfSpan.jpg","format":"Large","height":263,"width":395},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-jumbo.jpg","format":"Jumbo","height":683,"width":1024},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-superJumbo.jpg","format":"superJumbo","height":1366,"width":2048},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/02mexico-top-square640.jpg","format":"square640","height":640,"width":640},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/02mexico-top-thumbLarge.jpg","format":"Large Thumbnail","height":150,"width":150},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-mediumThreeByTwo210.jpg","format":"mediumThreeByTwo210","height":140,"width":210},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-mediumThreeByTwo440.jpg","format":"mediumThreeByTwo440","height":293,"width":440}]}]
     */

    @SerializedName("url")
    private String url;
    @SerializedName("adx_keywords")
    private String adxKeywords;
    @SerializedName("section")
    private String section;
    @SerializedName("byline")
    private String byline;
    @SerializedName("type")
    private String type;
    @SerializedName("title")
    private String title;
    @SerializedName("abstract")
    private String abstractX;
    @SerializedName("published_date")
    private String publishedDate;
    @SerializedName("source")
    private String source;
    @SerializedName("id")
    private long id;
    @SerializedName("asset_id")
    private long assetId;
    @SerializedName("views")
    private int views;
    @SerializedName("media")
    private List<MediaEntity> media;

    public ResultsEntity() {
    }

    protected ResultsEntity(Parcel in) {
        this.url = in.readString();
        this.adxKeywords = in.readString();
        this.section = in.readString();
        this.byline = in.readString();
        this.type = in.readString();
        this.title = in.readString();
        this.abstractX = in.readString();
        this.publishedDate = in.readString();
        this.source = in.readString();
        this.id = in.readLong();
        this.assetId = in.readLong();
        this.views = in.readInt();
        this.media = new ArrayList<MediaEntity>();
        in.readList(this.media, MediaEntity.class.getClassLoader());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public List<MediaEntity> getMedia() {
        return media;
    }

    public void setMedia(List<MediaEntity> media) {
        this.media = media;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.adxKeywords);
        dest.writeString(this.section);
        dest.writeString(this.byline);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.abstractX);
        dest.writeString(this.publishedDate);
        dest.writeString(this.source);
        dest.writeLong(this.id);
        dest.writeLong(this.assetId);
        dest.writeInt(this.views);
        dest.writeList(this.media);
    }
}
