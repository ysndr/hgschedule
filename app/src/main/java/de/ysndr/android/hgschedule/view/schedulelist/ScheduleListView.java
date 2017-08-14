package de.ysndr.android.hgschedule.view.schedulelist;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.ysndr.android.hgschedule.R;
import de.ysndr.android.hgschedule.state.State;
import de.ysndr.android.hgschedule.state.models.Entry;
import de.ysndr.android.hgschedule.view.StateController;
import fj.data.Option;
import io.reactivex.Observable;
import timber.log.Timber;

public class ScheduleListView extends LinearLayout implements ScheduleListMviViewInterface {

    @BindView(R.id.content_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.content_swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    StateController controller;
    RecyclerView.Adapter adapter;

    /*
    * Intents
    * */

    BehaviorRelay<Entry> dialogRequestIntent$;
    BehaviorRelay<Entry> filterRequestIntent$;
    Relay<Object> swipeRefreshIntent$;


    public ScheduleListView(Context context) {
        this(context, null);
    }

    public ScheduleListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScheduleListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.view_schedule_list, this);
        ButterKnife.bind(this);

        dialogRequestIntent$ = BehaviorRelay.create();
        filterRequestIntent$ = BehaviorRelay.create();
        swipeRefreshIntent$ = PublishRelay.create();

        RxSwipeRefreshLayout.refreshes(swipeRefresh)
            .doOnNext(obj -> Timber.d("swipe refresh triggered"))
            .subscribe(swipeRefreshIntent$);

        controller = new StateController(
            recyclerView.getRecycledViewPool(),
            dialogRequestIntent$,
            filterRequestIntent$);

        adapter = controller.getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }



    /*
    * Intents
    * */
    @Override
    public Observable<Entry> dialogIntent$() {
        return dialogRequestIntent$;
    }

    @Override
    public Observable<Entry> filterIntent$() {
        return filterRequestIntent$;
    }

    @Override
    public Observable<Object> reloadIntent$() {
        return RxSwipeRefreshLayout.refreshes(swipeRefresh)
            .doOnNext(__ -> Timber.d("reload Intent.."));
        //return swipeRefreshIntent$.doOnNext();
    }

    /*
    * Draw
    * */
    @Override
    public void render(State state) {
        state.union().continued(
            error -> {
                swipeRefresh.setRefreshing(false);
                this.toast(error.message().orSome("Internal error"));
            },
            scheduleData -> swipeRefresh.setRefreshing(scheduleData.loading()),
            // `setRefreshig == true` ≙ loading state
            empty -> swipeRefresh.setRefreshing(empty.loading())
        );
        controller.setData(state);
    }


    void toast(String message) {
        Toast toast = Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void dialog(DialogFragment d) {


    }

}