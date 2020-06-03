package com.unfpa.safepal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.unfpa.safepal.adapters.FaqAdapter;
import com.unfpa.safepal.provider.faqtable.FaqtableSelection;

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        RecyclerView faqRecycler = findViewById(R.id.faq_recycler);
        faqRecycler.setLayoutManager(new LinearLayoutManager(this));
        faqRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        faqRecycler.setAdapter(new FaqAdapter(this, new FaqtableSelection().query(this.getContentResolver())));
    }
}
