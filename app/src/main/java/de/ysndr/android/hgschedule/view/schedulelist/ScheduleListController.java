package de.ysndr.android.hgschedule.view.schedulelist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.MviController;

import de.ysndr.android.hgschedule.MyApp;
import de.ysndr.android.hgschedule.R;
import de.ysndr.android.hgschedule.state.State;
import de.ysndr.android.hgschedule.state.models.Entry;
import de.ysndr.android.hgschedule.state.models.Schedule;
import de.ysndr.android.hgschedule.view.ScheduleDialog;
import de.ysndr.android.hgschedule.view.ScheduleDialogBuilder;
import io.reactivex.Observable;

/**
 * Created by yannik on 7/19/17.
 */

public class ScheduleListController
    extends MviController<ScheduleListMviViewInterface, ScheduleListPresenter>
    implements ScheduleListMviViewInterface {

    private ScheduleListView view;

    public ScheduleListController() {
        this(null);
    }

    public ScheduleListController(Bundle args) {
        super(args);
        setRetainViewMode(RetainViewMode.RELEASE_DETACH);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_schedule_list, container, false);
        this.view = (ScheduleListView) view;
        return view;
    }

    @NonNull
    @Override
    public ScheduleListPresenter createPresenter() {
        return MyApp.getScheduleComponent(this.getActivity()).getPresenter();
    }

//    @NonNull
//    @Override
//    public ScheduleListMviView getMvpView() {
//        return this.view;
//    }

    @Override
    public void setRestoringViewState(boolean restoringViewState) {
        // what to do here?
    }

    @Override
    public Observable<Entry> dialogIntent$() {
        return view.dialogRequestIntent$;
    }

    @Override
    public Observable<Entry> filterIntent$() {
        return view.filterRequestIntent$;
    }

    @Override
    public Observable<Object> reloadIntent$() {
        return view.swipeRefreshIntent$;
    }

    @Override
    public void render(State state) {
        state.union().continued(
            error -> {},
            scheduleData -> {},
            entryDialogData -> {

                AppCompatActivity activity = ((AppCompatActivity) getActivity());
                FragmentManager fm = activity.getSupportFragmentManager();

                Entry entry = entryDialogData.entry();

                ScheduleDialog dialog = ScheduleDialogBuilder.newScheduleDialog(
                    entry.date().day(),
                    entry.info());

                dialog.show(fm, "tag");
            },
            empty -> {}
            );
        view.render(state);
    }
}
