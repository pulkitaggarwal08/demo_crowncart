package com.innovative.crownkart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.innovative.crownkart.R;
import com.innovative.crownkart.dto.CategoryDTO;
import com.innovative.crownkart.dto.SubcategoryDTO;
import com.innovative.crownkart.views.CustomTextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Pulkit on 20-Jun-17.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CategoryDTO> categoryDTOList;
    private HashMap<String, List<SubcategoryDTO>> listHashMap;

    public ExpandableAdapter(Context context, List<CategoryDTO> categoryDTOList, HashMap<String, List<SubcategoryDTO>> listHashMap) {
        this.context = context;
        this.categoryDTOList = categoryDTOList;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return this.categoryDTOList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listHashMap.get(this.categoryDTOList.get(groupPosition).getMainCategoryName()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categoryDTOList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listHashMap.get(this.categoryDTOList.get(groupPosition).getMainCategoryName()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_maincategory_item, parent, false);
        }
        CustomTextView tvName = (CustomTextView) convertView.findViewById(R.id.tv_maincategory);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_maincategory_icon);

        CategoryDTO categoryDTO = (CategoryDTO) getGroup(groupPosition);
        tvName.setText(categoryDTO.getMainCategoryName());

        if(!isExpanded){
            imageView.setImageResource(R.mipmap.right_arrow);
        }else{
            imageView.setImageResource(R.mipmap.down_arrow);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_subcategory_item, parent, false);
        }
        CustomTextView tvName = (CustomTextView) convertView.findViewById(R.id.expandedListItem);

        SubcategoryDTO subcategoryDTO = (SubcategoryDTO) getChild(groupPosition, childPosition);
        tvName.setText(subcategoryDTO.getSubCategoryName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
