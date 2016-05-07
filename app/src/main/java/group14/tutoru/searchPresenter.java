package group14.tutoru;

/**
 * Created by Dan Revie on 5/7/2016.
 */
public class searchPresenter {
    private ThisSearchView view;
    private PerformSearch performSearch;

    public searchPresenter(ThisSearchView view, PerformSearch search)
    {
        this.view = view;
        this.performSearch = search;
    }

    public void listenerForSearch() {
        String subject = view.getSubject();
        if(subject.isEmpty())
        {
            view.showSubjectError(R.string.SubjectError);
        }

        Integer priceBand = view.getPriceBand();
        if(priceBand <= 1)
        {
            view.showSpinnerError(R.string.PriceBandError);
        }

    }
}
