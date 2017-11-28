package com.dongwukj.weiwei.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dongwukj.weiwei.idea.result.ExtProductAttributeGroup;
import com.dongwukj.weiwei.idea.result.ProductAttribute;
import com.dongwukj.weiwei.idea.result.ProductAttributeValue;

import java.util.List;

/**
 * Created by sunjaly on 15/4/2.
 */
public class FragmentProductDetailAttributes extends Fragment {

    private LinearLayout productDetailAttributesPanel;
    List<ExtProductAttributeGroup> extProductAttributeGroupList;

    public void setProductAttributeValueList(List<ExtProductAttributeGroup> extProductAttributeGroupList) {
        this.extProductAttributeGroupList = extProductAttributeGroupList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        productDetailAttributesPanel=new LinearLayout(getActivity());
        productDetailAttributesPanel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        productDetailAttributesPanel.setOrientation(LinearLayout.VERTICAL);
        updateUI();
        return productDetailAttributesPanel;
    }

    public void updateUI(){
        if(extProductAttributeGroupList !=null){
            productDetailAttributesPanel.removeAllViews();
            Display display=getActivity().getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics=new DisplayMetrics();
            display.getMetrics(displayMetrics);
            int cum1Width=(int)(displayMetrics.widthPixels/3);
            int cun2Width=displayMetrics.widthPixels-cum1Width;

            for(ExtProductAttributeGroup extProductAttributeGroup: extProductAttributeGroupList){
                LinearLayout groupLinearLayout=new LinearLayout(getActivity());
                groupLinearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams groupLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                groupLayoutParams.setMargins(10,30,10,30);
                groupLinearLayout.setLayoutParams(groupLayoutParams);


                TextView groupTextView=new TextView(getActivity());
                groupTextView.setGravity(Gravity.CENTER);
                groupTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                groupTextView.setText(extProductAttributeGroup.getAttrGroupName());

                groupLinearLayout.addView(groupTextView);

                for(ProductAttribute productAttribute:extProductAttributeGroup.getAttributes()){
                    if(productAttribute.getAttributeValues()!=null && productAttribute.getAttributeValues().size()>0){
                        ProductAttributeValue productAttributeValue=productAttribute.getAttributeValues().get(0);

                        LinearLayout childLinearLayout=new LinearLayout(getActivity());
                        LinearLayout.LayoutParams childLayoutParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        groupLayoutParams.setMargins(0,10,0,10);
                        childLinearLayout.setLayoutParams(childLayoutParam);
                        childLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        TextView attributeName=new TextView(getActivity());
                        attributeName.setGravity(Gravity.RIGHT);
                        attributeName.setText(productAttributeValue.getAttrName()+"：");
                        attributeName.setLayoutParams(new LinearLayout.LayoutParams(cum1Width, LinearLayout.LayoutParams.WRAP_CONTENT));
                        childLinearLayout.addView(attributeName);

                        TextView attributeValue=new TextView(getActivity());
                        attributeValue.setGravity(Gravity.LEFT);
                        attributeValue.setPadding(20,0,0,0);
                        attributeValue.setText(productAttributeValue.getAttrValue());
                        attributeValue.setLayoutParams(new LinearLayout.LayoutParams(cun2Width, LinearLayout.LayoutParams.WRAP_CONTENT));
                        childLinearLayout.addView(attributeValue);

                        groupLinearLayout.addView(childLinearLayout);

                    }

                }

                productDetailAttributesPanel.addView(groupLinearLayout);

            }
        }
    }



}
