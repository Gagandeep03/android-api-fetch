package com.androidsample.ui.activity.fragment.task1.adapter;

import android.content.Context;
import android.databinding.ObservableField;

import com.androidsample.beans.ResultLiveBean;
import com.androidsample.roomdatabase.tables.ResultsEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

public class ListItemViewModelTest {
    @Mock
    Context context;
    @Mock
    ResultsEntity trayBean;

    ListItemViewModel.TrayItemModelListener listener;
    @Mock
    ObservableField<String> tv_title;
    @Mock
    ObservableField<String> tv_writtenBy;
    @Mock
    ObservableField<String> tv_displayTime;

    ListItemViewModel listItemViewModel;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        context = mock(Context.class);
        listener = mock(ListItemViewModel.TrayItemModelListener.class);
    }

    @Test
    public void testListItemViewModelValidValue() {
        try {
            listItemViewModel = spy(new ListItemViewModel(context, getValidBean(), listener, 1));
            Assert.assertEquals("2018-06-30", listItemViewModel.tv_displayTime.get());
            Assert.assertEquals("Is Neymar Black? Brazil and the Painful Relativity of Race", listItemViewModel.tv_title.get());
            Assert.assertEquals("By CLEUCI de OLIVEIRA", listItemViewModel.tv_writtenBy.get());
            Assert.assertEquals("https://static01.nyt.com/images/2018/06/30/opinion/30oliviera-new/30oliviera-new-thumbLarge.jpg", listItemViewModel.getImageUrl());
        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }

    @Test
    public void testListItemViewModelInValidValue() {
        try {
            listItemViewModel = spy(new ListItemViewModel(context, getEmptyResponseInValidBean(), listener, 1));
            Assert.assertEquals("", listItemViewModel.tv_displayTime.get());
            Assert.assertEquals("", listItemViewModel.tv_title.get());
            Assert.assertEquals("", listItemViewModel.tv_writtenBy.get());
            Assert.assertEquals("https://static01.nyt.com/images/2018/06/30/opinion/30oliviera-new/30oliviera-new-thumbLarge.jpg", listItemViewModel.getImageUrl());
        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }

    @Test
    public void testDefaultValueViewModel() {
        try {
            listItemViewModel = spy(new ListItemViewModel(null, null, null, 0));
            Assert.assertEquals(null, listItemViewModel.tv_displayTime.get());
            Assert.assertEquals(null, listItemViewModel.tv_title.get());
            Assert.assertEquals(null, listItemViewModel.tv_writtenBy.get());
            Assert.assertEquals(null, listItemViewModel.getImageUrl());


        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }

    @Test
    public void testOnItemClick() throws Exception {
        try {
            ResultLiveBean entity = getValidBean();
            listItemViewModel = spy(new ListItemViewModel(context, entity, listener, 1));
            listItemViewModel.onItemClick();
            Mockito.verify(listener, times(1)).onItemClick(1, entity);
        } catch (Exception e) {
            Assert.fail("Test fail");
        }
    }


    @Test
    public void testOnItemClickWithNullListener() throws Exception {
        try {
            ResultLiveBean entity = getValidBean();
            listItemViewModel = spy(new ListItemViewModel(context, entity, null, 1));
            listItemViewModel.onItemClick();
            Mockito.verify(listener, never()).onItemClick(1, entity);
        } catch (Exception e) {
            Assert.fail("Test fail");
        }
    }

    private ResultLiveBean getValidBean() {
        String RESPONSE_STRING = " {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/06\\/30\\/opinion\\/is-neymar-black-brazil-and-the-painful-relativity-of-race.html\",\n" +
                "\t\t\"adx_keywords\": \"Race and Ethnicity;Discrimination;Politics and Government;Affirmative Action;Sociology;Slavery (Historical);Blacks;Ebony (Magazine);Foreign Policy (Magazine);United Nations;Barcelona (Soccer Team);Neymar;Pele (1940- );Brazil;Africa;Santos (Brazil);United States;South America\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"Opinion\",\n" +
                "\t\t\"byline\": \"By CLEUCI de OLIVEIRA\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"Is Neymar Black? Brazil and the Painful Relativity of Race\",\n" +
                "\t\t\"abstract\": \"Ever since his \\u201cIt\\u2019s not like I\\u2019m black, you know?\\u201d comment, Neymar has served as a focal point in Brazil\\u2019s cultural reckoning with racism, whitening, identity and public policy.\",\n" +
                "\t\t\"published_date\": \"2018-06-30\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005983987,\n" +
                "\t\t\"asset_id\": 100000005983987,\n" +
                "\t\t\"views\": 16,\n" +
                "\t\t\"des_facet\": [\"RACE AND ETHNICITY\", \"DISCRIMINATION\"],\n" +
                "\t\t\"org_facet\": [\"POLITICS AND GOVERNMENT\", \"AFFIRMATIVE ACTION\", \"SOCIOLOGY\", \"SLAVERY (HISTORICAL)\", \"BLACKS\", \"EBONY (MAGAZINE)\", \"FOREIGN POLICY (MAGAZINE)\", \"UNITED NATIONS\", \"BARCELONA (SOCCER TEAM)\"],\n" +
                "\t\t\"per_facet\": [\"NEYMAR\", \"PELE (1940- )\"],\n" +
                "\t\t\"geo_facet\": [\"BRAZIL\", \"AFRICA\", \"SANTOS (BRAZIL)\", \"UNITED STATES\", \"SOUTH AMERICA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Neymar da Silva Santos J\\u00fanior, center, celebrating a goal with his teammates during Brazil\\u2019s World Cup match against Serbia on Wednesday.\",\n" +
                "\t\t\t\"copyright\": \"Michael Steele\\/Getty Images\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/merlin_140390226_e11cc18b-2f48-4217-8d4d-8b2f677534c2-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 117,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/merlin_140390226_e11cc18b-2f48-4217-8d4d-8b2f677534c2-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 631,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/merlin_140390226_e11cc18b-2f48-4217-8d4d-8b2f677534c2-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1263,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}";
        Gson gson = new Gson();
        ResultLiveBean apiResponse = gson.fromJson(RESPONSE_STRING,
                new TypeToken<ResultLiveBean>() {
                }.getType());
        return apiResponse;
    }

    private ResultLiveBean getEmptyResponseInValidBean() {
        String RESPONSE_STRING = " {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/06\\/30\\/opinion\\/is-neymar-black-brazil-and-the-painful-relativity-of-race.html\",\n" +
                "\t\t\"adx_keywords\": \"Race and Ethnicity;Discrimination;Politics and Government;Affirmative Action;Sociology;Slavery (Historical);Blacks;Ebony (Magazine);Foreign Policy (Magazine);United Nations;Barcelona (Soccer Team);Neymar;Pele (1940- );Brazil;Africa;Santos (Brazil);United States;South America\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"Opinion\",\n" +
                "\t\t\"byline\": \"\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"\",\n" +
                "\t\t\"abstract\": \"Ever since his \\u201cIt\\u2019s not like I\\u2019m black, you know?\\u201d comment, Neymar has served as a focal point in Brazil\\u2019s cultural reckoning with racism, whitening, identity and public policy.\",\n" +
                "\t\t\"published_date\": \"\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005983987,\n" +
                "\t\t\"asset_id\": 100000005983987,\n" +
                "\t\t\"views\": 16,\n" +
                "\t\t\"des_facet\": [\"RACE AND ETHNICITY\", \"DISCRIMINATION\"],\n" +
                "\t\t\"org_facet\": [\"POLITICS AND GOVERNMENT\", \"AFFIRMATIVE ACTION\", \"SOCIOLOGY\", \"SLAVERY (HISTORICAL)\", \"BLACKS\", \"EBONY (MAGAZINE)\", \"FOREIGN POLICY (MAGAZINE)\", \"UNITED NATIONS\", \"BARCELONA (SOCCER TEAM)\"],\n" +
                "\t\t\"per_facet\": [\"NEYMAR\", \"PELE (1940- )\"],\n" +
                "\t\t\"geo_facet\": [\"BRAZIL\", \"AFRICA\", \"SANTOS (BRAZIL)\", \"UNITED STATES\", \"SOUTH AMERICA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Neymar da Silva Santos J\\u00fanior, center, celebrating a goal with his teammates during Brazil\\u2019s World Cup match against Serbia on Wednesday.\",\n" +
                "\t\t\t\"copyright\": \"Michael Steele\\/Getty Images\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/merlin_140390226_e11cc18b-2f48-4217-8d4d-8b2f677534c2-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 117,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/merlin_140390226_e11cc18b-2f48-4217-8d4d-8b2f677534c2-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 631,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/merlin_140390226_e11cc18b-2f48-4217-8d4d-8b2f677534c2-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1263,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/opinion\\/30oliviera-new\\/30oliviera-new-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}";
        Gson gson = new Gson();
        ResultLiveBean apiResponse = gson.fromJson(RESPONSE_STRING,
                new TypeToken<ResultLiveBean>() {
                }.getType());
        return apiResponse;
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme