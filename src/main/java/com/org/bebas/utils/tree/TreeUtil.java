package com.org.bebas.utils.tree;

import com.org.bebas.utils.tree.entity.TreeEntity;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树构建工具类
 *
 * @author WuHao
 * @since 2023/3/23 15:50
 */
public class TreeUtil<Model, TM extends TreeEntity<TM>> {

    private List<Model> modelList;

    private List<TM> treeList;

    public static <Model, TM extends TreeEntity<TM>> TreeUtil<Model, TM> build(List<Model> list) {
        TreeUtil<Model, TM> t = new TreeUtil<>();
        t.modelList = list;
        return t;
    }

    public TreeUtil<Model, TM> convert(Function<List<Model>, List<TM>> function) {
        this.treeList = function.apply(this.modelList);
        return this;
    }

    public List<TM> builder() {
        ConvertUtil<TM> convertUtil = new ConvertUtil<>(this.treeList);
        return convertUtil.convertTreeBuild();
    }

    /**
     * 根据父节点过滤
     *
     * @param list
     * @param parentId
     * @return
     */
    public static <TM extends TreeEntity<TM>> List<TM> filterParent(List<TM> list, Long parentId) {
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        List<TM> filterPIdList = list.stream().filter(item -> item.getId().equals(parentId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filterPIdList)) {
            List<TM> treeChildrenList = list.stream()
                    .flatMap(item -> item.getChildren().stream())
                    .collect(Collectors.toList());
            return filterParent(treeChildrenList, parentId);
        } else {
            return filterPIdList;
        }
    }

    @AllArgsConstructor
    private static class ConvertUtil<TM extends TreeEntity<TM>> {

        private List<TM> treeList;

        private List<TM> convertTreeBuild() {
            List<TM> treeList = this.treeList;
            if (CollectionUtils.isEmpty(treeList)) {
                return new ArrayList<>();
            }
            List<Long> tempList = treeList.parallelStream().map(TreeEntity::getId).collect(Collectors.toList());
            List<TM> returnList = treeList.stream()
                    .filter(item -> !tempList.contains(item.getParentId()))
                    .peek(item -> recursionList(treeList, item))
                    .collect(Collectors.toList());
            if (returnList.isEmpty()) {
                returnList = treeList;
            }
            return returnList;
        }

        /**
         * 递归列表
         *
         * @param list
         * @param t
         */
        private void recursionList(List<TM> list, TM t) {
            // 得到子节点列表
            List<TM> childList = getChildList(list, t);
            t.setChildren(childList);
            for (TM tChild : childList) {
                if (hasChild(list, tChild)) {
                    recursionList(list, tChild);
                }
            }
        }

        /**
         * 得到子节点列表
         *
         * @param list
         * @param t
         * @return
         */
        protected List<TM> getChildList(List<TM> list, TM t) {
            return list.stream().filter(item -> Objects.equals(item.getParentId(), t.getId())).collect(Collectors.toList());
        }

        /**
         * 判断是否有子节点
         *
         * @param list
         * @param t
         * @return
         */
        protected boolean hasChild(List<TM> list, TM t) {
            return getChildList(list, t).size() > 0;
        }

    }

    private List<Model> getModelList() {
        return modelList;
    }

    private void setModelList(List<Model> modelList) {
        this.modelList = modelList;
    }

    private List<TM> getTreeList() {
        return treeList;
    }

    private void setTreeList(List<TM> treeList) {
        this.treeList = treeList;
    }
}
