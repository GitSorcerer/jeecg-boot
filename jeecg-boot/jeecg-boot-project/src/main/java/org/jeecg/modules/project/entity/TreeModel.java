package org.jeecg.modules.project.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: GH
 * @Date: 2019/12/25 21:53
 * @Version 1.0
 */
@Data
public class TreeModel {
 /*class 	节点的 class 	string 	-
 isLeaf 	设置为叶子节点(设置了loadData时有效) 	boolean 	false
 scopedSlots 	使用columns时，可以通过该属性配置支持slot-scope的属性，如 scopedSlots: { title: 'XXX'} 	object 	-
 on 	事件对象，仅在treeNodes使用方式中生效，如{click: () => {}} 	object 	'---'*/
    //    private String Class;
    private String title;//标题 	string|slot|slot-scope 	'---'
    private String key;//被树的 (default)ExpandedKeys / (default)CheckedKeys / (default)SelectedKeys 属性所用。注意：整个树范围内的所有节点的 key 值不能重复！ 	string | number 	内部计算出的节点位置
//    private String style;//节点的 style 	string|object 	-
    private boolean disableCheckbox;//禁掉 checkbox 	boolean 	false
    private boolean disabled;//禁掉响应 	boolean 	false
//    private String icon;//自定义图标。可接收组件，props 为当前节点 props 	slot|slot-scope 	-
    private boolean isLeaf;//设置为叶子节点(设置了loadData时有效) 	boolean 	false
    private boolean selectable=true;//设置节点是否可被选中 	boolean 	true
//    private String slots;//使用treeNodes时，可以通过该属性配置支持slot的属性，如 slots: { title: 'XXX'} 	object 	-
//    private String scopedSlots;//使用columns时，可以通过该属性配置支持slot-scope的属性，如 scopedSlots: { title: 'XXX'} 	object 	-


    private String parentKey;
    private List<TreeModel> children;
}
