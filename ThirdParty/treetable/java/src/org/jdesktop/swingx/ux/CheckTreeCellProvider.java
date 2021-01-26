/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.swingx.ux;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.gsonformat.intellij.entity.FieldEntity;
import org.gsonformat.intellij.entity.ClassEntity;
import org.jdesktop.swingx.renderer.CellContext;
import org.jdesktop.swingx.renderer.ComponentProvider;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

/**
 *
 * @author vearn
 */
public class CheckTreeCellProvider extends ComponentProvider<JPanel> {

    private CheckTreeSelectionModel selectionModel;
    private TristateCheckBox _checkBox = null;
    private JLabel _label = null;

    public CheckTreeCellProvider(CheckTreeSelectionModel selectionModel) {
        this.selectionModel = selectionModel;
        _checkBox = new TristateCheckBox(); //  ����һ��TristateCheckBoxʵ��
        _checkBox.setOpaque(false); //  ����TristateCheckBox�����Ʊ���
        _label = new JLabel();  //  ����һ��JLabelʵ��
    }

    @Override
    protected void format(CellContext arg0) {
        //  ��CellContext��ȡtree�е����ֺ�ͼ��
        JTree tree = (JTree) arg0.getComponent();
        DefaultMutableTreeTableNode node = (DefaultMutableTreeTableNode) arg0.getValue();
        Object obj = node.getUserObject();
        if(obj instanceof FieldEntity){
            _label.setText(((FieldEntity) obj).getKey());
            _checkBox.setSelector((FieldEntity) obj);
        }else if(obj instanceof ClassEntity){
            _label.setText(((ClassEntity) obj).getClassName());
            _checkBox.setSelector((ClassEntity) obj);
        }

//        _label.setIcon(arg0.getIcon());

        //  ����selectionModel�е�״̬������TristateCheckBox�����
        TreePath path = tree.getPathForRow(arg0.getRow());
        if (path != null) {
            if (selectionModel.isPathSelected(path, true)) {
                _checkBox.setState(Boolean.TRUE);
            } else if (selectionModel.isPartiallySelected(path)) {
                _checkBox.setState(null);   //  ע�⡰����ѡ�С�״̬��API
            } else {
                _checkBox.setState(Boolean.FALSE);
            }
        }

        //  ʹ��BorderLayout���֣����η���TristateCheckBox��JLabel
        rendererComponent.setLayout(new BorderLayout());
        rendererComponent.add(_checkBox);
        rendererComponent.add(_label, BorderLayout.LINE_END);
    }

    @Override
    protected void configureState(CellContext arg0) {
    }

    /**
     * ��ʼ��һ��JPanel������TristateCheckBox��JLabel
     */
    @Override
    protected JPanel createRendererComponent() {
        JPanel panel = new JPanel();
        return panel;
    }
}