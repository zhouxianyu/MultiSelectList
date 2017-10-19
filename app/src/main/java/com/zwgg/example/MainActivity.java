package com.zwgg.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zwgg.multiselect.node.MultiSelectedNode;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SellerMultiSelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new SellerMultiSelectAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ArrayList<SellerMultiSelectedData> list = new ArrayList<>();
        MultiSelectedNode<SellerViewModel> node = new MultiSelectedNode<>(0);
        node.setViewModel(new SellerViewModel("总部"));
//        node.setExpand(true);
        for (int i = 0; i < 10; i++) {
            MultiSelectedNode<SellerViewModel> node1 = new MultiSelectedNode<>(1);
            node1.setViewModel(new SellerViewModel("部门" + i));
            node1.dataBinding(node, node1);
//            node1.setExpand(false);
            for (int j = 0; j < 10; j++) {
                MultiSelectedNode<SellerViewModel> node2 = new MultiSelectedNode<>(2);
                node2.setViewModel(new SellerViewModel("员工" + j));
                node2.dataBinding(node1, node2);
//                node2.setExpand(false);
            }
        }
        adapter.setData(node);
    }
}
