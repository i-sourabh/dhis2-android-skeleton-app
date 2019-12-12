package com.example.android.androidskeletonapp.ui.code_executor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.android.androidskeletonapp.R;
import com.example.android.androidskeletonapp.data.Sdk;
import com.example.android.androidskeletonapp.data.utils.Exercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.collect.Lists;

import org.hisp.dhis.android.core.category.CategoryOptionCombo;
import org.hisp.dhis.android.core.dataelement.DataElement;
import org.hisp.dhis.android.core.dataset.DataSet;
import org.hisp.dhis.android.core.dataset.DataSetElement;
import org.hisp.dhis.android.core.datavalue.DataValueObjectRepository;
import org.hisp.dhis.android.core.organisationunit.OrganisationUnit;
import org.hisp.dhis.android.core.period.Period;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CodeExecutorActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView executingNotificator;
    private TextView resultNotificator;

    private Disposable disposable;

    public static Intent getIntent(Context context) {
        return new Intent(context, CodeExecutorActivity.class);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_executor);
        Toolbar toolbar = findViewById(R.id.codeExecutorToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        executingNotificator = findViewById(R.id.codeExecutorNotificator);
        resultNotificator = findViewById(R.id.resultNotificator);
        progressBar = findViewById(R.id.codeExecutorProgressBar);
        FloatingActionButton codeExecutorButton = findViewById(R.id.codeExecutorButton);

        codeExecutorButton.setOnClickListener(view -> {
            view.setEnabled(Boolean.FALSE);
            view.setVisibility(View.INVISIBLE);
            Snackbar.make(view, "Executing...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            executingNotificator.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            resultNotificator.setVisibility(View.INVISIBLE);

            disposable = executeCode()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            result -> {
                                executingNotificator.setVisibility(View.INVISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                                resultNotificator.setText(result);
                                resultNotificator.setVisibility(View.VISIBLE);
                                view.setEnabled(Boolean.TRUE);
                                view.setVisibility(View.VISIBLE);
                            },
                            error -> {
                                error.printStackTrace();
                            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Exercise(
            exerciseNumber = "ex05a",
            version = 1,
            title = "Set all the data values of one data set instance",
            tips =  "Use the data set module to get one data set. \n" +
                    "Use the period module to get a period with the same period type that the data set. \n" +
                    "Use the organisation unit module to get one organisation unit.  \n" +
                    "Use the category module to get the attribute option combos related to the data set. \n" +
                    "Set a random value for each combination of data element, category option combo and attribute " +
                    "option combo (Using nested loops).\n",
            solutionBranch = "sol05a"
    )
    private Single<String> executeCode() {
        return Single.defer(() -> {
            // TODO resolve the exercise here.

            DataSet dataSet= Sdk.d2().dataSetModule().dataSets().withDataSetElements().one().blockingGet();

            Period period_this=Sdk.d2().periodModule().periodHelper().blockingGetPeriodsForDataSet(dataSet.uid()).get(0);

            OrganisationUnit organisationUnit=Sdk.d2().organisationUnitModule().organisationUnits().byDataSetUids(Lists.newArrayList(dataSet.uid())).one().blockingGet();

            List<CategoryOptionCombo> attributeOptionCombos = Sdk.d2().categoryModule().categoryOptionCombos()
                    .byCategoryComboUid().eq(dataSet.categoryCombo().uid())
                    .blockingGet();

            for (DataSetElement dataSetElement: dataSet.dataSetElements())
            {
                DataElement dataElement = Sdk.d2().dataElementModule().dataElements()
                        .uid(dataSetElement.dataElement().uid())
                        .blockingGet();
                List<CategoryOptionCombo> categoryOptionCombos = Sdk.d2().categoryModule().categoryOptionCombos()
                        .byCategoryComboUid().eq(dataElement.categoryCombo().uid())
                        .blockingGet();
                for (CategoryOptionCombo coc : categoryOptionCombos) {
                    for (CategoryOptionCombo aoc : attributeOptionCombos) {
                        DataValueObjectRepository repository = Sdk.d2().dataValueModule().dataValues()
                                .value(
                                        period_this.periodId(),
                                        organisationUnit.uid(),
                                        dataSetElement.dataElement().uid(),
                                        dataSetElement.categoryCombo() != null ?
                                                dataSetElement.categoryCombo().uid() :
                                                coc.uid(),
                                        aoc.uid());
                        repository.blockingSet("124");
                    }
                }

            }


            CategoryOptionCombo categoryOptionCombo=Sdk.d2().categoryModule().categoryOptionCombos().byCategoryComboUid()
            return Single.just("Execution done!");

            return Single.just("Execution done!");
        });
    }
}
