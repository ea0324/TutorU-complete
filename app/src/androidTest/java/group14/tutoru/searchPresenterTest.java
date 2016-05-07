package group14.tutoru;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.Exception;import group14.tutoru.PerformSearch;import group14.tutoru.R;import group14.tutoru.ThisSearchView;import group14.tutoru.searchPresenter;import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Dan Revie on 5/7/2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class searchPresenterTest {
    @Mock
    private ThisSearchView view;
    @Mock
    private PerformSearch service;
    @Mock
    private searchPresenter presenter;

    @Before
    public void setUp() throws Exception {

        presenter = new searchPresenter(view, service);
    }

    @Test
    public void showMessageWhenFieldsEmpty() throws Exception {
        when(view.getSubject()).thenReturn("");
        presenter.listenerForSearch();

        verify(view).showSubjectError(R.string.SubjectError);
        verify(view).showStarRatingError(R.string.StarError);
        verify(view).showSpinnerError(R.string.PriceBandError);

    }



}