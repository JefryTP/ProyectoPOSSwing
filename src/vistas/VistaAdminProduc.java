package vistas;
import controlador.CategoriaController;
import controlador.ProductoController;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.Producto;
public class VistaAdminProduc extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VistaAdminProduc.class.getName());
    private final ProductoController productoController = new ProductoController();
    private final CategoriaController categoriaController = new CategoriaController();

    private List<Categoria> listaCategorias = new ArrayList<>();
 
    private static final String[] OPCIONES_ESTADO = {"Activo", "Inactivo"};
 
    public VistaAdminProduc() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        configurarCombos();
    }
 
    private void configurarCombos() {
        // Categorías reales desde la BD
        listaCategorias = categoriaController.listar();
        if (listaCategorias.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron cargar las categorías. Verifique la conexión a la base de datos.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            String[] nombresCategorias = new String[listaCategorias.size()];
            for (int i = 0; i < listaCategorias.size(); i++) {
                nombresCategorias[i] = listaCategorias.get(i).getNombre();
            }
            cmbCate.setModel(new DefaultComboBoxModel<>(nombresCategorias));
        }
 
        // Estado: Activo / Inactivo
        cmbEst.setModel(new DefaultComboBoxModel<>(OPCIONES_ESTADO));
    }
 
    private void seleccionarCategoriaPorId(int idCategoria) {
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getId() == idCategoria) {
                cmbCate.setSelectedIndex(i);
                return;
            }
        }
        if (cmbCate.getItemCount() > 0) {
            cmbCate.setSelectedIndex(0);
        }
    }
 
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtDesc.setText("");
        txtPrecio.setText("");
        if (cmbCate.getItemCount() > 0) {
            cmbCate.setSelectedIndex(0);
        }
        cmbEst.setSelectedIndex(0);
    }

    private Double leerPrecioValido() {
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            return precio;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio ingresado no es válido.");
            return null;
        }
    }

    private Producto construirProductoDesdeFormulario(double precio) {
        Producto p = new Producto();
        p.setCodigo(txtCodigo.getText().trim());
        p.setDescripcion(txtDesc.getText().trim());
        p.setPrecio(precio);
        p.setIdCategoria(listaCategorias.get(cmbCate.getSelectedIndex()).getId());
        p.setActivo(cmbEst.getSelectedIndex() == 0); // 0 = "Activo"
        return p;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalir3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        cmbCate = new javax.swing.JComboBox<>();
        cmbEst = new javax.swing.JComboBox<>();
        btnAgregar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ProyectoPOS - Caja");

        btnSalir3.setBackground(new java.awt.Color(51, 51, 51));
        btnSalir3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSalir3.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir3.setText("SALIR");
        btnSalir3.setMargin(new java.awt.Insets(4, 28, 6, 28));
        btnSalir3.addActionListener(this::btnSalir3ActionPerformed);
        getContentPane().add(btnSalir3, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(139, 204, 237));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(139, 204, 237));

        txtCodigo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtCodigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtCodigo.setMargin(new java.awt.Insets(2, 8, 2, 8));

        btnBuscar.setBackground(new java.awt.Color(13, 13, 217));
        btnBuscar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setMargin(new java.awt.Insets(8, 28, 8, 28));
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setText("Estado:");

        btnEliminar.setBackground(new java.awt.Color(255, 0, 0));
        btnEliminar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setMargin(new java.awt.Insets(16, 28, 16, 28));
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Codigo:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Precio:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Categoria:");

        txtDesc.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDesc.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtDesc.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtDesc.addActionListener(this::txtDescActionPerformed);

        txtPrecio.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtPrecio.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtPrecio.setMargin(new java.awt.Insets(2, 8, 2, 8));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Descripcion:");

        btnGuardar.setBackground(new java.awt.Color(51, 0, 255));
        btnGuardar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setMargin(new java.awt.Insets(16, 28, 16, 28));
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Buscar:");

        txtBuscar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtBuscar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtBuscar.setMargin(new java.awt.Insets(2, 8, 2, 8));

        cmbCate.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbCate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCate.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        cmbEst.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbEst.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbEst.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnAgregar.setBackground(new java.awt.Color(0, 204, 0));
        btnAgregar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.setMargin(new java.awt.Insets(16, 28, 16, 28));
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel7.setText("Gestionar Productos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbEst, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCate, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBuscar)
                            .addComponent(txtCodigo)
                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnEliminar)
                            .addComponent(btnGuardar)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnBuscar)
                                .addComponent(btnAgregar)))))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(125, 125, 125))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel7)
                .addGap(89, 89, 89)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(94, 94, 94)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbCate, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEst, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63))
        );

        jPanel1.add(jPanel2, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescActionPerformed

    private void btnSalir3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir3ActionPerformed
        dispose();
        vistas.Menu ventanaMenu = new vistas.Menu();
        ventanaMenu.setVisible(true);
    }//GEN-LAST:event_btnSalir3ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        String codigo = txtCodigo.getText().trim();
 
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero busque un producto por su código.");
            return;
        }
 
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "Esta acción eliminará PERMANENTEMENTE el producto con código \"" + codigo + "\".\n"
                + "¿Desea continuar?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
 
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
 
        String mensaje = productoController.eliminar(codigo);
        JOptionPane.showMessageDialog(this, mensaje);
 
        if (!mensaje.contains("Error")) {
            limpiarCampos();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
                String codigo = txtBuscar.getText().trim();
 
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código para buscar.");
            return;
        }

        Producto p = productoController.buscarPorCodigoAdmin(codigo);
 
        if (p == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró ningún producto con ese código.\n"
                    + "Puede crear uno nuevo completando los datos y usando \"Agregar\".");
            limpiarCampos();
            txtCodigo.setText(codigo); // deja el código listo para registrar uno nuevo
            return;
        }
 
        txtCodigo.setText(p.getCodigo());
        txtDesc.setText(p.getDescripcion());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        seleccionarCategoriaPorId(p.getIdCategoria());
        cmbEst.setSelectedIndex(p.isActivo() ? 0 : 1);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
                String codigo = txtCodigo.getText().trim();
 
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese o busque un código de producto.");
            return;
        }
        if (txtDesc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la descripción del producto.");
            return;
        }
        if (listaCategorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay categorías cargadas; no se puede guardar.");
            return;
        }
 
        Double precio = leerPrecioValido();
        if (precio == null) {
            return;
        }
 
        Producto producto = construirProductoDesdeFormulario(precio);
 
        // modificar() ya valida internamente que el código SÍ exista en la BD
        String mensaje = productoController.modificar(producto);
        JOptionPane.showMessageDialog(this, mensaje);
 
        if (!mensaje.contains("Error")) {
            limpiarCampos();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
                String codigo = txtCodigo.getText().trim();
 
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del nuevo producto.");
            return;
        }
        if (txtDesc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la descripción del producto.");
            return;
        }
        if (listaCategorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay categorías cargadas; no se puede agregar.");
            return;
        }
 
        Double precio = leerPrecioValido();
        if (precio == null) {
            return;
        }
 
        Producto producto = construirProductoDesdeFormulario(precio);
 
        // registrar() ya valida internamente que el código NO exista todavía
        String mensaje = productoController.registrar(producto);
        JOptionPane.showMessageDialog(this, mensaje);
 
        if (!mensaje.contains("Error")) {
            limpiarCampos();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VistaAdminProduc().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir3;
    private javax.swing.JComboBox<String> cmbCate;
    private javax.swing.JComboBox<String> cmbEst;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
