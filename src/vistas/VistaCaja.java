package vistas;

import controlador.ProductoController;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.ItemVenta;
import modelo.Producto;

public class VistaCaja extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VistaCaja.class.getName());

 // ================== LÓGICA DE NEGOCIO (agregada) ==================
 
    private final ProductoController productoController = new ProductoController();
    private final List<ItemVenta> itemsVenta = new ArrayList<>();
    private static final String[] COLUMNAS_TABLA = {"Código", "Producto", "Cantidad", "Precio", "Subtotal"};
 
    public VistaCaja() {
        initComponents();
        configurarTabla();
        configurarListeners();
    }
 
    /**
     * Reemplaza el modelo de tabla que dejó el diseñador visual (4 columnas
     * "Title 1..4" con 4 filas vacías) por el modelo real que necesita la
     * caja: 5 columnas, sin filas iniciales, y no editable directamente.
     */
    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(COLUMNAS_TABLA, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVenta.setModel(modelo);
        tablaVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
        // Total inicial
        txtTotalPagar.setEditable(false);
        txtTotalPagar.setText("S/. 0.00");
 
        // Cantidad mínima de 1 (evita agregar con cantidad 0)
        spiCant.setModel(new javax.swing.SpinnerNumberModel(1, 1, 999, 1));
 
        // Quitar solo se activa al seleccionar una fila
        btnQuitarProd.setEnabled(false);
    }
 
    private void configurarListeners() {
        btnAgregarProd.addActionListener(e -> agregarProducto());
        btnQuitarProd.addActionListener(e -> quitarProductoSeleccionado());
 
        tablaVenta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnQuitarProd.setEnabled(tablaVenta.getSelectedRow() != -1);
            }
        });
 
        btnPagar.addActionListener(e
                -> JOptionPane.showMessageDialog(this, "Próximamente", "Pagar", JOptionPane.INFORMATION_MESSAGE));
    }
 
    private void agregarProducto() {
        String codigo = txtIdProd.getText().trim();
        int cantidad = (int) spiCant.getValue();
 
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de producto.");
            return;
        }
 
        // El controlador ya maneja sus propias excepciones y devuelve null si falla
        Producto producto = productoController.buscarPorCodigo(codigo);
 
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un producto activo con ese código.");
            return;
        }
 
        ItemVenta existente = buscarItemPorCodigo(producto.getCodigo());
        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + cantidad);
            actualizarFilaEnTabla(existente);
        } else {
            ItemVenta nuevoItem = new ItemVenta(
                    producto.getCodigo(),
                    producto.getDescripcion(),
                    cantidad,
                    producto.getPrecio()
            );
            itemsVenta.add(nuevoItem);
            agregarFilaATabla(nuevoItem);
        }
 
        recalcularTotal();
        limpiarCamposEntrada();
    }
 
    private void quitarProductoSeleccionado() {
        int fila = tablaVenta.getSelectedRow();
        if (fila == -1) {
            return;
        }
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        String codigo = (String) modelo.getValueAt(fila, 0);
        itemsVenta.removeIf(item -> item.getCodigo().equals(codigo));
        modelo.removeRow(fila);
        btnQuitarProd.setEnabled(false);
        recalcularTotal();
    }
 
    private ItemVenta buscarItemPorCodigo(String codigo) {
        for (ItemVenta item : itemsVenta) {
            if (item.getCodigo().equals(codigo)) {
                return item;
            }
        }
        return null;
    }
 
    private void agregarFilaATabla(ItemVenta item) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        modelo.addRow(new Object[]{
            item.getCodigo(),
            item.getDescripcion(),
            item.getCantidad(),
            String.format("%.2f", item.getPrecioUnitario()),
            String.format("%.2f", item.getSubtotal())
        });
    }
 
    private void actualizarFilaEnTabla(ItemVenta item) {
        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (modelo.getValueAt(i, 0).equals(item.getCodigo())) {
                modelo.setValueAt(item.getCantidad(), i, 2);
                modelo.setValueAt(String.format("%.2f", item.getSubtotal()), i, 4);
                break;
            }
        }
    }
 
    private void recalcularTotal() {
        double total = 0;
        for (ItemVenta item : itemsVenta) {
            total += item.getSubtotal();
        }
        txtTotalPagar.setText(String.format("S/. %.2f", total));
    }
 
    private void limpiarCamposEntrada() {
        txtIdProd.setText("");
        spiCant.setValue(1);
        txtIdProd.requestFocus();
    }
 
    // ================== FIN LÓGICA DE NEGOCIO ==================
public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new VistaCaja().setVisible(true));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cmbDoc = new javax.swing.JComboBox<>();
        txtNumDoc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIdProd = new javax.swing.JTextField();
        spiCant = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        btnAgregarProd = new javax.swing.JButton();
        btnQuitarProd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtTotalPagar = new javax.swing.JTextField();
        btnPagar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(720, 480));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Documento Identidad:");

        cmbDoc.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtNumDoc.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Producto:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Cantidad:");

        txtIdProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        spiCant.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Numero documento:");

        btnAgregarProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnAgregarProd.setText("AGREGAR");

        btnQuitarProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnQuitarProd.setText("QUITAR");

        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaVenta);

        btnSalir.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Total a Pagar");

        txtTotalPagar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnPagar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnPagar.setText("Pagar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(cmbDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtNumDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(spiCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                                .addComponent(btnAgregarProd)
                                .addGap(35, 35, 35)
                                .addComponent(btnQuitarProd)
                                .addGap(85, 85, 85))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSalir)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPagar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdProd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnSalir)))
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spiCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarProd)
                    .addComponent(btnQuitarProd))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPagar))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
        vistas.Menu ventanaMenu = new vistas.Menu();
        ventanaMenu.setVisible(true);
    }//GEN-LAST:event_btnSalirActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProd;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnQuitarProd;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cmbDoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spiCant;
    private javax.swing.JTable tablaVenta;
    private javax.swing.JTextField txtIdProd;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtTotalPagar;
    // End of variables declaration//GEN-END:variables
}
