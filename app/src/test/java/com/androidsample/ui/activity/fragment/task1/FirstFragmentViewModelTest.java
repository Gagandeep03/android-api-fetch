package com.androidsample.ui.activity.fragment.task1;

import android.databinding.ObservableBoolean;

import com.androidsample.api.ApiInterface;
import com.androidsample.beans.ApiResponse;
import com.androidsample.roomdatabase.tables.MediaEntity;
import com.androidsample.roomdatabase.tables.MediaMetadataEntity;
import com.androidsample.roomdatabase.tables.ResultsEntity;
import com.androidsample.utils.schedulers.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FirstFragmentViewModelTest {

    ApiInterface apiInterface;
    @Mock
    List<ResultsEntity> list;
    @Mock
    ObservableBoolean mIsLoading;

    SchedulerProvider mSchedulerProvider;
    @Mock
    FirstFragmentPresenterView.View mNavigator;
    //Field mCompositeDisposable of type CompositeDisposable - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @InjectMocks
    FirstFragmentViewModel firstFragmentViewModel;

    TestScheduler testScheduler = new TestScheduler();
    TestSchedulerProvider testSchedulerProvider;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // Set Scheduler
        testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        firstFragmentViewModel.setmSchedulerProvider(testSchedulerProvider);
        apiInterface = mock(ApiInterface.class);
        // mock the interface for view
        mNavigator = mock(FirstFragmentPresenterView.View.class);
        firstFragmentViewModel.setmNavigator(mNavigator);
        firstFragmentViewModel.onViewCreated();

    }

    @Test
    public void testLoadApiWithValidResponse() {
        try {
            when(apiInterface.getMostPopularList("date", "apiKey")).thenReturn(getPositiveResponse());
            when(mNavigator.insertResultEntry(any(ResultsEntity.class))).thenReturn(1l);
            when(mNavigator.insertMediaEntry(any(MediaEntity.class))).thenReturn(1l);
            FirstFragmentViewModel firstFragmentViewModel = getFirstFragmentViewModel();
            firstFragmentViewModel.loadApi("date", "apiKey");
            testScheduler.triggerActions();
            verify(mNavigator, atLeast(1)).insertMediaMetaList(ArgumentMatchers.<MediaMetadataEntity>anyList());
            verify(mNavigator, never()).showToast(anyString());
            verify(mNavigator, never()).getStringIds(anyInt());
            verify(mNavigator, times(1)).hideLoading();
        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }

    @Test
    public void testLoadApiWithInValidResponse() {
        try {
            when(apiInterface.getMostPopularList("date", "apiKey")).thenReturn(getInValidResponse());
            when(mNavigator.getStringIds(anyInt())).thenReturn("Error");
            FirstFragmentViewModel firstFragmentViewModel = getFirstFragmentViewModel();
            firstFragmentViewModel.loadApi("date", "apiKey");
            testScheduler.triggerActions();
            //  verify(mNavigator, never()).setAdapter(ArgumentMatchers.<ResultsEntity>anyList());
            verify(mNavigator, times(1)).showToast("Error");
            verify(mNavigator, times(2)).getStringIds(anyInt());

        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }

    @Test
    public void testLoadApiWithInValidCredentialResponse() {
        try {
            when(apiInterface.getMostPopularList("date", "apiKey")).thenReturn(getEmptyResponse());
            when(mNavigator.getStringIds(anyInt())).thenReturn("Error");
            FirstFragmentViewModel firstFragmentViewModel = getFirstFragmentViewModel();
            firstFragmentViewModel.loadApi("date", "apiKey");
            testScheduler.triggerActions();
            //   verify(mNavigator, never()).setAdapter(ArgumentMatchers.<ResultsEntity>anyList());
            verify(mNavigator, times(1)).showToast("Error");
            verify(mNavigator, times(2)).getStringIds(anyInt());
            verify(mNavigator, times(1)).hideLoading();
        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }


    @Test
    public void testLoadApiWithNullResponse() {
        try {
            when(apiInterface.getMostPopularList("date", "apiKey"))
                    .thenReturn(Observable.fromCallable(new Callable<ApiResponse>() {
                        @Override
                        public ApiResponse call() throws Exception {
                            return new ApiResponse();
                        }
                    }));
            when(mNavigator.getStringIds(anyInt())).thenReturn("Error");
            FirstFragmentViewModel firstFragmentViewModel = getFirstFragmentViewModel();
            firstFragmentViewModel.loadApi("date", "apiKey");
            testScheduler.triggerActions();
            //        verify(mNavigator, never()).setAdapter(ArgumentMatchers.<ResultsEntity>anyList());
            verify(mNavigator, times(1)).showToast("Error");
            verify(mNavigator, times(2)).getStringIds(anyInt());
            verify(mNavigator, times(1)).hideLoading();
        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }

    @Test
    public void testLoadApiWithTimeOutResponse() {
        try {
            when(apiInterface.getMostPopularList("date", "apiKey"))
                    .thenReturn(Observable.fromCallable(new Callable<ApiResponse>() {
                        @Override
                        public ApiResponse call() throws Exception {
                            Thread.sleep(2500);
                            return getPositiveResponseNoObserveable();
                        }
                    }).timeout(2, TimeUnit.SECONDS));
            when(mNavigator.getStringIds(anyInt())).thenReturn("Error");
            FirstFragmentViewModel firstFragmentViewModel = getFirstFragmentViewModel();
            firstFragmentViewModel.loadApi("date", "apiKey");
            testScheduler.triggerActions();
            //    verify(mNavigator, never()).setAdapter(ArgumentMatchers.<ResultsEntity>anyList());
            verify(mNavigator, times(1)).showToast("Error");
            verify(mNavigator, times(1)).getStringIds(anyInt());
            verify(mNavigator, times(1)).hideLoading();
        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }

    @Test
    public void testLoadApiWithNullDate() {
        try {
            when(apiInterface.getMostPopularList(null, "apiKey"))
                    .thenReturn(getPositiveResponse());
            when(mNavigator.getStringIds(anyInt())).thenReturn("Error");
            FirstFragmentViewModel firstFragmentViewModel = getFirstFragmentViewModel();
            firstFragmentViewModel.loadApi(null, "apiKey");
            testScheduler.triggerActions();
            verify(firstFragmentViewModel, never()).loadApi(anyString(), anyString());
            //   verify(mNavigator, never()).setAdapter(ArgumentMatchers.<ResultsEntity>anyList());
            verify(mNavigator, times(1)).showToast("Error");
            verify(mNavigator, times(1)).getStringIds(anyInt());
        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }

    @Test
    public void testLoadApiWithNullApiKey() {
        try {
            when(apiInterface.getMostPopularList("date", null))
                    .thenReturn(getPositiveResponse());
            when(mNavigator.getStringIds(anyInt())).thenReturn("Error");
            FirstFragmentViewModel firstFragmentViewModel = getFirstFragmentViewModel();
            firstFragmentViewModel.loadApi("date", null);
            testScheduler.triggerActions();
            verify(firstFragmentViewModel, never()).loadApi(anyString(), anyString());
            //   verify(mNavigator, never()).setAdapter(ArgumentMatchers.<ResultsEntity>anyList());
            verify(mNavigator, times(1)).showToast("Error");
            verify(mNavigator, times(1)).getStringIds(anyInt());
        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }


    @Test
    public void testOnViewCreated() {
        firstFragmentViewModel.onViewCreated();
    }

    @Test
    public void testOnDestroyView() {
        firstFragmentViewModel.onDestroyView();
    }

    @Test
    public void testSetIsLoading() {
        firstFragmentViewModel.setIsLoading(true);
    }

    //------------------------------------------------------------
    // ************************  MOCKING CLASS AND METHODS ***********************
    // ------------------------------------------------------------

    private FirstFragmentViewModel getFirstFragmentViewModel() {
        FirstFragmentViewModel firstFragmentViewModel = spy(new FirstFragmentViewModel(testSchedulerProvider, apiInterface));
        firstFragmentViewModel.setmNavigator(mNavigator);
        firstFragmentViewModel.setmSchedulerProvider(testSchedulerProvider);
        firstFragmentViewModel.onViewCreated();

        return firstFragmentViewModel;
    }

    private Observable<ApiResponse> getEmptyResponse() {
        String RESPONSE_STRING = "{\"message\":\"Invalid authentication credentials\"}";
        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(RESPONSE_STRING,
                new TypeToken<ApiResponse>() {
                }.getType());

        return Observable.just(apiResponse);
    }

    private ApiResponse getPositiveResponseNoObserveable() {
        String RESPONSE_STRING = "{\n" +
                "\t\"status\": \"OK\",\n" +
                "\t\"copyright\": \"Copyright (c) 2018 The New York Times Company.  All Rights Reserved.\",\n" +
                "\t\"num_results\": 1726,\n" +
                "\t\"results\": [  {\n" +
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
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/06\\/30\\/sports\\/world-cup\\/argentina-vs-france.html\",\n" +
                "\t\t\"adx_keywords\": \"World Cup 2018 (Soccer);Soccer;France;Argentina;Mbappe, Kylian (1998- );Messi, Lionel\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"Sports\",\n" +
                "\t\t\"byline\": \"By CHRISTOPHER CLAREY\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"Messi Exits the World Cup. Hours Later, So Does Ronaldo.\",\n" +
                "\t\t\"abstract\": \"Messi, and Argentina, fell victim to Kylian Mbapp\\u00e9, who scored twice for France. And Edinson Cavani scored twice in Uruguay\\u2019s victory over Ronaldo and Portugal.\",\n" +
                "\t\t\"published_date\": \"2018-06-30\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005984176,\n" +
                "\t\t\"asset_id\": 100000005984176,\n" +
                "\t\t\"views\": 17,\n" +
                "\t\t\"des_facet\": [\"WORLD CUP 2018 (SOCCER)\"],\n" +
                "\t\t\"org_facet\": [\"SOCCER\"],\n" +
                "\t\t\"per_facet\": [\"MBAPPE, KYLIAN (1998- )\", \"MESSI, LIONEL\"],\n" +
                "\t\t\"geo_facet\": [\"FRANCE\", \"ARGENTINA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Argentina\\u2019s players, including Lionel Messi, middle, reacted after losing to France.\",\n" +
                "\t\t\t\"copyright\": \"Saeed Khan\\/Agence France-Presse \\u2014 Getty Images\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 127,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 683,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1365,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/07\\/07\\/world\\/asia\\/mike-pompeo-north-korea-pyongyang.html\",\n" +
                "\t\t\"adx_keywords\": \"North Korea;Kim Jong-un;Nuclear Weapons;Pompeo, Mike;Trump, Donald J;United States International Relations;Arms Control and Limitation and Disarmament\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"World\",\n" +
                "\t\t\"byline\": \"By GARDINER HARRIS and CHOE SANG-HUN\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"North Korea Criticizes \\u2018Gangster-Like\\u2019 U.S. Attitude After Talks With Mike Pompeo\",\n" +
                "\t\t\"abstract\": \"North Korea\\u2019s Foreign Ministry accused the Trump administration of pushing a \\u201cgangster-like demand for denuclearization.\\u201d But Secretary of State Mike Pompeo called the talks \\u201cproductive.\\u201d\",\n" +
                "\t\t\"published_date\": \"2018-07-07\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005995955,\n" +
                "\t\t\"asset_id\": 100000005995955,\n" +
                "\t\t\"views\": 18,\n" +
                "\t\t\"des_facet\": [\"UNITED STATES INTERNATIONAL RELATIONS\", \"ARMS CONTROL AND LIMITATION AND DISARMAMENT\"],\n" +
                "\t\t\"org_facet\": [\"NUCLEAR WEAPONS\"],\n" +
                "\t\t\"per_facet\": [\"KIM JONG-UN\", \"POMPEO, MIKE\", \"TRUMP, DONALD J\"],\n" +
                "\t\t\"geo_facet\": [\"NORTH KOREA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Secretary of State Mike Pompeo with the North Korean official Kim Yong-chol on Saturday in Pyongyang.\",\n" +
                "\t\t\t\"copyright\": \"Pool photo by Andrew Harnik\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 127,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 683,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1365,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/07\\/06\\/sports\\/world-cup\\/brazil-vs-belgium.html\",\n" +
                "\t\t\"adx_keywords\": \"Soccer;World Cup 2018 (Soccer);Belgium;Brazil\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"Sports\",\n" +
                "\t\t\"byline\": \"By RORY SMITH\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"As Brazil Crashes Out, the Magic Appears to Be Gone, Too\",\n" +
                "\t\t\"abstract\": \"Belgium scored twice in the first half as Brazil\\u2019s run at the World Cup ended in stunned silence on Friday, 2-1.\",\n" +
                "\t\t\"published_date\": \"2018-07-06\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005993073,\n" +
                "\t\t\"asset_id\": 100000005993073,\n" +
                "\t\t\"views\": 19,\n" +
                "\t\t\"des_facet\": [\"SOCCER\", \"WORLD CUP 2018 (SOCCER)\"],\n" +
                "\t\t\"org_facet\": \"\",\n" +
                "\t\t\"per_facet\": \"\",\n" +
                "\t\t\"geo_facet\": [\"BELGIUM\", \"BRAZIL\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Belgium players celebrate after Brazil\\u2019s Fernandinho scored an own goal.\",\n" +
                "\t\t\t\"copyright\": \"Andre Penner\\/Associated Press\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/merlin_140875116_5d78695b-db75-4310-95bf-f91dae3293b2-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 138,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 264,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/merlin_140875116_5d78695b-db75-4310-95bf-f91dae3293b2-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 746,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/merlin_140875116_5d78695b-db75-4310-95bf-f91dae3293b2-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1492,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/07\\/03\\/world\\/africa\\/woman-kills-giraffe.html\",\n" +
                "\t\t\"adx_keywords\": \"Talley, Tunessa Thompson;Hunting and Trapping;Giraffes;South Africa;Conservation of Resources;Palmer, Walter J;Poaching (Wildlife);Endangered and Extinct Species;Animal Abuse, Rights and Welfare\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"World\",\n" +
                "\t\t\"byline\": \"By CHRISTINE HAUSER\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"Killing of African Giraffe Sets Off Anger at \\u2018White American Savage\\u2019 Who Shot It\",\n" +
                "\t\t\"abstract\": \"Photographs of an American woman with the giraffe she shot in South Africa last year have fueled an online debate about big-game trophy hunting.\",\n" +
                "\t\t\"published_date\": \"2018-07-03\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005988707,\n" +
                "\t\t\"asset_id\": 100000005988707,\n" +
                "\t\t\"views\": 20,\n" +
                "\t\t\"des_facet\": [\"HUNTING AND TRAPPING\", \"CONSERVATION OF RESOURCES\", \"ANIMAL ABUSE, RIGHTS AND WELFARE\"],\n" +
                "\t\t\"org_facet\": [\"GIRAFFES\", \"POACHING (WILDLIFE)\", \"ENDANGERED AND EXTINCT SPECIES\"],\n" +
                "\t\t\"per_facet\": [\"TALLEY, TUNESSA THOMPSON\", \"PALMER, WALTER J\"],\n" +
                "\t\t\"geo_facet\": [\"SOUTH AFRICA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Photographs of an American woman with a giraffe she killed in South Africa have been widely circulated on social media.\",\n" +
                "\t\t\t\"copyright\": \"\",\n" +
                "\t\t\t\"approved_for_syndication\": 0,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 134,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 724,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 843,\n" +
                "\t\t\t\t\"width\": 1193\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}]\n" +
                "}";

        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(RESPONSE_STRING,
                new TypeToken<ApiResponse>() {
                }.getType());

        return apiResponse;
    }

    private Observable<ApiResponse> getPositiveResponse() {
        String RESPONSE_STRING = "{\n" +
                "\t\"status\": \"OK\",\n" +
                "\t\"copyright\": \"Copyright (c) 2018 The New York Times Company.  All Rights Reserved.\",\n" +
                "\t\"num_results\": 1726,\n" +
                "\t\"results\": [  {\n" +
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
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/06\\/30\\/sports\\/world-cup\\/argentina-vs-france.html\",\n" +
                "\t\t\"adx_keywords\": \"World Cup 2018 (Soccer);Soccer;France;Argentina;Mbappe, Kylian (1998- );Messi, Lionel\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"Sports\",\n" +
                "\t\t\"byline\": \"By CHRISTOPHER CLAREY\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"Messi Exits the World Cup. Hours Later, So Does Ronaldo.\",\n" +
                "\t\t\"abstract\": \"Messi, and Argentina, fell victim to Kylian Mbapp\\u00e9, who scored twice for France. And Edinson Cavani scored twice in Uruguay\\u2019s victory over Ronaldo and Portugal.\",\n" +
                "\t\t\"published_date\": \"2018-06-30\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005984176,\n" +
                "\t\t\"asset_id\": 100000005984176,\n" +
                "\t\t\"views\": 17,\n" +
                "\t\t\"des_facet\": [\"WORLD CUP 2018 (SOCCER)\"],\n" +
                "\t\t\"org_facet\": [\"SOCCER\"],\n" +
                "\t\t\"per_facet\": [\"MBAPPE, KYLIAN (1998- )\", \"MESSI, LIONEL\"],\n" +
                "\t\t\"geo_facet\": [\"FRANCE\", \"ARGENTINA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Argentina\\u2019s players, including Lionel Messi, middle, reacted after losing to France.\",\n" +
                "\t\t\t\"copyright\": \"Saeed Khan\\/Agence France-Presse \\u2014 Getty Images\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 127,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 683,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1365,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/07\\/07\\/world\\/asia\\/mike-pompeo-north-korea-pyongyang.html\",\n" +
                "\t\t\"adx_keywords\": \"North Korea;Kim Jong-un;Nuclear Weapons;Pompeo, Mike;Trump, Donald J;United States International Relations;Arms Control and Limitation and Disarmament\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"World\",\n" +
                "\t\t\"byline\": \"By GARDINER HARRIS and CHOE SANG-HUN\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"North Korea Criticizes \\u2018Gangster-Like\\u2019 U.S. Attitude After Talks With Mike Pompeo\",\n" +
                "\t\t\"abstract\": \"North Korea\\u2019s Foreign Ministry accused the Trump administration of pushing a \\u201cgangster-like demand for denuclearization.\\u201d But Secretary of State Mike Pompeo called the talks \\u201cproductive.\\u201d\",\n" +
                "\t\t\"published_date\": \"2018-07-07\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005995955,\n" +
                "\t\t\"asset_id\": 100000005995955,\n" +
                "\t\t\"views\": 18,\n" +
                "\t\t\"des_facet\": [\"UNITED STATES INTERNATIONAL RELATIONS\", \"ARMS CONTROL AND LIMITATION AND DISARMAMENT\"],\n" +
                "\t\t\"org_facet\": [\"NUCLEAR WEAPONS\"],\n" +
                "\t\t\"per_facet\": [\"KIM JONG-UN\", \"POMPEO, MIKE\", \"TRUMP, DONALD J\"],\n" +
                "\t\t\"geo_facet\": [\"NORTH KOREA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Secretary of State Mike Pompeo with the North Korean official Kim Yong-chol on Saturday in Pyongyang.\",\n" +
                "\t\t\t\"copyright\": \"Pool photo by Andrew Harnik\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 127,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 683,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1365,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/07\\/06\\/sports\\/world-cup\\/brazil-vs-belgium.html\",\n" +
                "\t\t\"adx_keywords\": \"Soccer;World Cup 2018 (Soccer);Belgium;Brazil\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"Sports\",\n" +
                "\t\t\"byline\": \"By RORY SMITH\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"As Brazil Crashes Out, the Magic Appears to Be Gone, Too\",\n" +
                "\t\t\"abstract\": \"Belgium scored twice in the first half as Brazil\\u2019s run at the World Cup ended in stunned silence on Friday, 2-1.\",\n" +
                "\t\t\"published_date\": \"2018-07-06\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005993073,\n" +
                "\t\t\"asset_id\": 100000005993073,\n" +
                "\t\t\"views\": 19,\n" +
                "\t\t\"des_facet\": [\"SOCCER\", \"WORLD CUP 2018 (SOCCER)\"],\n" +
                "\t\t\"org_facet\": \"\",\n" +
                "\t\t\"per_facet\": \"\",\n" +
                "\t\t\"geo_facet\": [\"BELGIUM\", \"BRAZIL\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Belgium players celebrate after Brazil\\u2019s Fernandinho scored an own goal.\",\n" +
                "\t\t\t\"copyright\": \"Andre Penner\\/Associated Press\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/merlin_140875116_5d78695b-db75-4310-95bf-f91dae3293b2-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 138,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 264,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/merlin_140875116_5d78695b-db75-4310-95bf-f91dae3293b2-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 746,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/merlin_140875116_5d78695b-db75-4310-95bf-f91dae3293b2-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1492,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/07\\/03\\/world\\/africa\\/woman-kills-giraffe.html\",\n" +
                "\t\t\"adx_keywords\": \"Talley, Tunessa Thompson;Hunting and Trapping;Giraffes;South Africa;Conservation of Resources;Palmer, Walter J;Poaching (Wildlife);Endangered and Extinct Species;Animal Abuse, Rights and Welfare\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"World\",\n" +
                "\t\t\"byline\": \"By CHRISTINE HAUSER\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"Killing of African Giraffe Sets Off Anger at \\u2018White American Savage\\u2019 Who Shot It\",\n" +
                "\t\t\"abstract\": \"Photographs of an American woman with the giraffe she shot in South Africa last year have fueled an online debate about big-game trophy hunting.\",\n" +
                "\t\t\"published_date\": \"2018-07-03\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005988707,\n" +
                "\t\t\"asset_id\": 100000005988707,\n" +
                "\t\t\"views\": 20,\n" +
                "\t\t\"des_facet\": [\"HUNTING AND TRAPPING\", \"CONSERVATION OF RESOURCES\", \"ANIMAL ABUSE, RIGHTS AND WELFARE\"],\n" +
                "\t\t\"org_facet\": [\"GIRAFFES\", \"POACHING (WILDLIFE)\", \"ENDANGERED AND EXTINCT SPECIES\"],\n" +
                "\t\t\"per_facet\": [\"TALLEY, TUNESSA THOMPSON\", \"PALMER, WALTER J\"],\n" +
                "\t\t\"geo_facet\": [\"SOUTH AFRICA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Photographs of an American woman with a giraffe she killed in South Africa have been widely circulated on social media.\",\n" +
                "\t\t\t\"copyright\": \"\",\n" +
                "\t\t\t\"approved_for_syndication\": 0,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 134,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 724,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 843,\n" +
                "\t\t\t\t\"width\": 1193\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}]\n" +
                "}";

        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(RESPONSE_STRING,
                new TypeToken<ApiResponse>() {
                }.getType());

        return Observable.just(apiResponse);
    }

    private Observable<ApiResponse> getInValidResponse() {
        String RESPONSE_STRING = "{\n" +
                "\t\"status\": \"Fail\",\n" +
                "\t\"copyright\": \"Copyright (c) 2018 The New York Times Company.  All Rights Reserved.\",\n" +
                "\t\"num_results\": 1726,\n" +
                "\t\"results\": [  {\n" +
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
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/06\\/30\\/sports\\/world-cup\\/argentina-vs-france.html\",\n" +
                "\t\t\"adx_keywords\": \"World Cup 2018 (Soccer);Soccer;France;Argentina;Mbappe, Kylian (1998- );Messi, Lionel\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"Sports\",\n" +
                "\t\t\"byline\": \"By CHRISTOPHER CLAREY\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"Messi Exits the World Cup. Hours Later, So Does Ronaldo.\",\n" +
                "\t\t\"abstract\": \"Messi, and Argentina, fell victim to Kylian Mbapp\\u00e9, who scored twice for France. And Edinson Cavani scored twice in Uruguay\\u2019s victory over Ronaldo and Portugal.\",\n" +
                "\t\t\"published_date\": \"2018-06-30\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005984176,\n" +
                "\t\t\"asset_id\": 100000005984176,\n" +
                "\t\t\"views\": 17,\n" +
                "\t\t\"des_facet\": [\"WORLD CUP 2018 (SOCCER)\"],\n" +
                "\t\t\"org_facet\": [\"SOCCER\"],\n" +
                "\t\t\"per_facet\": [\"MBAPPE, KYLIAN (1998- )\", \"MESSI, LIONEL\"],\n" +
                "\t\t\"geo_facet\": [\"FRANCE\", \"ARGENTINA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Argentina\\u2019s players, including Lionel Messi, middle, reacted after losing to France.\",\n" +
                "\t\t\t\"copyright\": \"Saeed Khan\\/Agence France-Presse \\u2014 Getty Images\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 127,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 683,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1365,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/france-new-topper-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/06\\/30\\/sports\\/france-new-topper\\/merlin_140538354_317c6e57-aa01-419c-8869-2bbdcfbedca0-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/07\\/07\\/world\\/asia\\/mike-pompeo-north-korea-pyongyang.html\",\n" +
                "\t\t\"adx_keywords\": \"North Korea;Kim Jong-un;Nuclear Weapons;Pompeo, Mike;Trump, Donald J;United States International Relations;Arms Control and Limitation and Disarmament\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"World\",\n" +
                "\t\t\"byline\": \"By GARDINER HARRIS and CHOE SANG-HUN\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"North Korea Criticizes \\u2018Gangster-Like\\u2019 U.S. Attitude After Talks With Mike Pompeo\",\n" +
                "\t\t\"abstract\": \"North Korea\\u2019s Foreign Ministry accused the Trump administration of pushing a \\u201cgangster-like demand for denuclearization.\\u201d But Secretary of State Mike Pompeo called the talks \\u201cproductive.\\u201d\",\n" +
                "\t\t\"published_date\": \"2018-07-07\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005995955,\n" +
                "\t\t\"asset_id\": 100000005995955,\n" +
                "\t\t\"views\": 18,\n" +
                "\t\t\"des_facet\": [\"UNITED STATES INTERNATIONAL RELATIONS\", \"ARMS CONTROL AND LIMITATION AND DISARMAMENT\"],\n" +
                "\t\t\"org_facet\": [\"NUCLEAR WEAPONS\"],\n" +
                "\t\t\"per_facet\": [\"KIM JONG-UN\", \"POMPEO, MIKE\", \"TRUMP, DONALD J\"],\n" +
                "\t\t\"geo_facet\": [\"NORTH KOREA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Secretary of State Mike Pompeo with the North Korean official Kim Yong-chol on Saturday in Pyongyang.\",\n" +
                "\t\t\t\"copyright\": \"Pool photo by Andrew Harnik\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 127,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 683,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1365,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/08\\/world\\/08POMPEO-alpha\\/08POMPEO-alpha-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/07\\/06\\/sports\\/world-cup\\/brazil-vs-belgium.html\",\n" +
                "\t\t\"adx_keywords\": \"Soccer;World Cup 2018 (Soccer);Belgium;Brazil\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"Sports\",\n" +
                "\t\t\"byline\": \"By RORY SMITH\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"As Brazil Crashes Out, the Magic Appears to Be Gone, Too\",\n" +
                "\t\t\"abstract\": \"Belgium scored twice in the first half as Brazil\\u2019s run at the World Cup ended in stunned silence on Friday, 2-1.\",\n" +
                "\t\t\"published_date\": \"2018-07-06\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005993073,\n" +
                "\t\t\"asset_id\": 100000005993073,\n" +
                "\t\t\"views\": 19,\n" +
                "\t\t\"des_facet\": [\"SOCCER\", \"WORLD CUP 2018 (SOCCER)\"],\n" +
                "\t\t\"org_facet\": \"\",\n" +
                "\t\t\"per_facet\": \"\",\n" +
                "\t\t\"geo_facet\": [\"BELGIUM\", \"BRAZIL\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Belgium players celebrate after Brazil\\u2019s Fernandinho scored an own goal.\",\n" +
                "\t\t\t\"copyright\": \"Andre Penner\\/Associated Press\",\n" +
                "\t\t\t\"approved_for_syndication\": 1,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/merlin_140875116_5d78695b-db75-4310-95bf-f91dae3293b2-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 138,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 264,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/merlin_140875116_5d78695b-db75-4310-95bf-f91dae3293b2-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 746,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/merlin_140875116_5d78695b-db75-4310-95bf-f91dae3293b2-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 1492,\n" +
                "\t\t\t\t\"width\": 2048\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/07\\/sports\\/07brazil-belgium-jubo2\\/07brazil-belgium-jubo2-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"url\": \"https:\\/\\/www.nytimes.com\\/2018\\/07\\/03\\/world\\/africa\\/woman-kills-giraffe.html\",\n" +
                "\t\t\"adx_keywords\": \"Talley, Tunessa Thompson;Hunting and Trapping;Giraffes;South Africa;Conservation of Resources;Palmer, Walter J;Poaching (Wildlife);Endangered and Extinct Species;Animal Abuse, Rights and Welfare\",\n" +
                "\t\t\"column\": null,\n" +
                "\t\t\"section\": \"World\",\n" +
                "\t\t\"byline\": \"By CHRISTINE HAUSER\",\n" +
                "\t\t\"type\": \"Article\",\n" +
                "\t\t\"title\": \"Killing of African Giraffe Sets Off Anger at \\u2018White American Savage\\u2019 Who Shot It\",\n" +
                "\t\t\"abstract\": \"Photographs of an American woman with the giraffe she shot in South Africa last year have fueled an online debate about big-game trophy hunting.\",\n" +
                "\t\t\"published_date\": \"2018-07-03\",\n" +
                "\t\t\"source\": \"The New York Times\",\n" +
                "\t\t\"id\": 100000005988707,\n" +
                "\t\t\"asset_id\": 100000005988707,\n" +
                "\t\t\"views\": 20,\n" +
                "\t\t\"des_facet\": [\"HUNTING AND TRAPPING\", \"CONSERVATION OF RESOURCES\", \"ANIMAL ABUSE, RIGHTS AND WELFARE\"],\n" +
                "\t\t\"org_facet\": [\"GIRAFFES\", \"POACHING (WILDLIFE)\", \"ENDANGERED AND EXTINCT SPECIES\"],\n" +
                "\t\t\"per_facet\": [\"TALLEY, TUNESSA THOMPSON\", \"PALMER, WALTER J\"],\n" +
                "\t\t\"geo_facet\": [\"SOUTH AFRICA\"],\n" +
                "\t\t\"media\": [{\n" +
                "\t\t\t\"type\": \"image\",\n" +
                "\t\t\t\"subtype\": \"photo\",\n" +
                "\t\t\t\"caption\": \"Photographs of an American woman with a giraffe she killed in South Africa have been widely circulated on social media.\",\n" +
                "\t\t\t\"copyright\": \"\",\n" +
                "\t\t\t\"approved_for_syndication\": 0,\n" +
                "\t\t\t\"media-metadata\": [{\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-square320.jpg\",\n" +
                "\t\t\t\t\"format\": \"square320\",\n" +
                "\t\t\t\t\"height\": 320,\n" +
                "\t\t\t\t\"width\": 320\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-thumbStandard.jpg\",\n" +
                "\t\t\t\t\"format\": \"Standard Thumbnail\",\n" +
                "\t\t\t\t\"height\": 75,\n" +
                "\t\t\t\t\"width\": 75\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-articleInline.jpg\",\n" +
                "\t\t\t\t\"format\": \"Normal\",\n" +
                "\t\t\t\t\"height\": 134,\n" +
                "\t\t\t\t\"width\": 190\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-sfSpan.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large\",\n" +
                "\t\t\t\t\"height\": 263,\n" +
                "\t\t\t\t\"width\": 395\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-jumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"Jumbo\",\n" +
                "\t\t\t\t\"height\": 724,\n" +
                "\t\t\t\t\"width\": 1024\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-superJumbo.jpg\",\n" +
                "\t\t\t\t\"format\": \"superJumbo\",\n" +
                "\t\t\t\t\"height\": 843,\n" +
                "\t\t\t\t\"width\": 1193\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-square640.jpg\",\n" +
                "\t\t\t\t\"format\": \"square640\",\n" +
                "\t\t\t\t\"height\": 640,\n" +
                "\t\t\t\t\"width\": 640\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-thumbLarge.jpg\",\n" +
                "\t\t\t\t\"format\": \"Large Thumbnail\",\n" +
                "\t\t\t\t\"height\": 150,\n" +
                "\t\t\t\t\"width\": 150\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-mediumThreeByTwo210.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo210\",\n" +
                "\t\t\t\t\"height\": 140,\n" +
                "\t\t\t\t\"width\": 210\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"url\": \"https:\\/\\/static01.nyt.com\\/images\\/2018\\/07\\/04\\/us\\/04xp-giraffe-print\\/04xp-giraffe-mediumThreeByTwo440.jpg\",\n" +
                "\t\t\t\t\"format\": \"mediumThreeByTwo440\",\n" +
                "\t\t\t\t\"height\": 293,\n" +
                "\t\t\t\t\"width\": 440\n" +
                "\t\t\t}]\n" +
                "\t\t}]\n" +
                "\t}]\n" +
                "}";

        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(RESPONSE_STRING,
                new TypeToken<ApiResponse>() {
                }.getType());

        return Observable.just(apiResponse);
    }

    public class TestSchedulerProvider extends SchedulerProvider {

        private final TestScheduler mTestScheduler;

        public TestSchedulerProvider(TestScheduler testScheduler) {
            this.mTestScheduler = testScheduler;
        }

        @Override
        public Scheduler ui() {
            return mTestScheduler;
        }

        @Override
        public Scheduler computation() {
            return mTestScheduler;
        }

        @Override
        public Scheduler io() {
            return mTestScheduler;
        }

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme