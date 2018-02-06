# MultiSelectList

### 效果图
![about-gif](http://7xteal.com1.z0.glb.clouddn.com/multi_select_gif.gif)

### 如何使用
1. 创建Model继承MultiSelectNode

```public class SellerViewModel extends MultiSelectNode<SellerViewModel>{

    public SellerViewModel(String text,int viewType) {
        super(viewType);
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
```


2. 创建Adapter继承MultiSelectAdapter<Model>，复写Adapter的onCreateViewHolder与onBindViewHolder方法

```  public class SellerMultiSelectAdapter extends MultiSelectAdapter<SellerViewModel> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0 || viewType == 1) {
            return new TitleViewHolder(parent);
        } else if (viewType == 2) {
            return new ItemViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SellerViewModel item = getItem(position);
        if (holder instanceof ItemViewBinder) {
            ((ItemViewBinder) holder).bindView(item);
        }
    }
```



