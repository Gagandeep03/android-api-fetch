package com.androidsample.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MediaEntity {

    /**
     * type : image
     * subtype : photo
     * caption : Andrés Manuel López Obrador campaigned on a narrative of social change, including increased pensions for the elderly, educational grants for Mexico’s youth and additional support for farmers.
     * copyright : Manuel Velasquez/Getty Images
     * approved_for_syndication : 1
     * media-metadata : [{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/02mexico-top-square320.jpg","format":"square320","height":320,"width":320},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/02mexico-top-thumbStandard.jpg","format":"Standard Thumbnail","height":75,"width":75},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-articleInline.jpg","format":"Normal","height":127,"width":190},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-sfSpan.jpg","format":"Large","height":263,"width":395},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-jumbo.jpg","format":"Jumbo","height":683,"width":1024},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-superJumbo.jpg","format":"superJumbo","height":1366,"width":2048},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/02mexico-top-square640.jpg","format":"square640","height":640,"width":640},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/02mexico-top-thumbLarge.jpg","format":"Large Thumbnail","height":150,"width":150},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-mediumThreeByTwo210.jpg","format":"mediumThreeByTwo210","height":140,"width":210},{"url":"https://static01.nyt.com/images/2018/07/02/world/02mexico-top/merlin_140627415_dc13f473-7d2b-4997-803b-0cb1c54c8653-mediumThreeByTwo440.jpg","format":"mediumThreeByTwo440","height":293,"width":440}]
     */

    @SerializedName("type")
    private String type;
    @SerializedName("subtype")
    private String subtype;
    @SerializedName("caption")
    private String caption;
    @SerializedName("copyright")
    private String copyright;
    @SerializedName("approved_for_syndication")
    private int approvedForSyndication;
    @SerializedName("media-metadata")
    private List<MediametadataEntity> mediametadata;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getApprovedForSyndication() {
        return approvedForSyndication;
    }

    public void setApprovedForSyndication(int approvedForSyndication) {
        this.approvedForSyndication = approvedForSyndication;
    }

    public List<MediametadataEntity> getMediametadata() {
        return mediametadata;
    }

    public void setMediametadata(List<MediametadataEntity> mediametadata) {
        this.mediametadata = mediametadata;
    }
}
