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
        MultiSelectNode<SellerViewModel> node = new MultiSelectNode<>(0);
        node.setViewModel(new SellerViewModel("总部"));
        node.setExpand(true);
        for (int i = 0; i < 3; i++) {
            MultiSelectNode<SellerViewModel> node1 = new MultiSelectNode<>(1);
            node1.setViewModel(new SellerViewModel("部门" + i));
            node1.dataBinding(node, node1);
            for (int j = 0; j < 20; j++) {
                MultiSelectNode<SellerViewModel> node2 = new MultiSelectNode<>(2);
                node2.setViewModel(new SellerViewModel("员工" + j));
                node2.dataBinding(node1, node2);
            }
        }

        adapter.setData(node);
    }
}
