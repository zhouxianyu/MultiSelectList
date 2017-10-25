package com.zwgg.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zwgg.multiselect.MultiSelectAdapter;
import com.zwgg.multiselect.node.MultiSelectNode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SellerMultiSelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new SellerMultiSelectAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SellerViewModel node = new SellerViewModel("总部", 0);
        node.setExpand(true);
        for (int i = 0; i < 3; i++) {
            SellerViewModel node1 = new SellerViewModel("部门" + i, 1);
            node1.dataBinding(node, node1);
            for (int j = 0; j < 20; j++) {
                SellerViewModel node2 = new SellerViewModel("员工" + j, 2);
                node2.dataBinding(node1, node2);
            }
        }

        adapter.setData(node);
    }
}
