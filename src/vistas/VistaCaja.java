package vistas;

import controlador.ProductoController;
import controlador.UsuarioController;
import controlador.VentaController;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import modelo.Comprobante;
import modelo.ItemVenta;
import modelo.Producto;
import modelo.Sesion;
import modelo.Usuario;
import modelo.Venta;

public class VistaCaja extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VistaCaja.class.getName());

 // ================== LÓGICA DE NEGOCIO ==================
    private final ProductoController productoController = new ProductoController();
    private final VentaController ventaController = new VentaController();
    private final UsuarioController usuarioController = new UsuarioController();
    private final List<ItemVenta> itemsVenta = new ArrayList<>();
    private static final String[] COLUMNAS_TABLA = {"Código", "Producto", "Cantidad", "Precio", "Subtotal"};
 
    public VistaCaja() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        configurarTabla();
        configurarListeners();
    }

    private void configurarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(COLUMNAS_TABLA, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVenta.setModel(modelo);
        tablaVenta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
        txtTotalPagar.setEditable(false);
        txtTotalPagar.setText("S/. 0.00");
 
        // Cantidad mínima de 1 (evita agregar con cantidad 0)
        spiCant.setModel(new javax.swing.SpinnerNumberModel(1, 1, 999, 1));
 
        // Quitar botón
        btnQuitarProd.setEnabled(false);

        cmbDoc.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"DNI", "RUC", "Carnet de Extranjería"}));
    }
 
    private void configurarListeners() {
        tablaVenta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnQuitarProd.setEnabled(tablaVenta.getSelectedRow() != -1);
            }
        });
    }
 
    private void agregarProducto() {
        String codigo = txtIdProd.getText().trim();
        int cantidad = (int) spiCant.getValue();
 
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de producto.");
            return;
        }
 
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
 
    private double calcularTotalActual() {
        double total = 0;
        for (ItemVenta item : itemsVenta) {
            total += item.getSubtotal();
        }
        return total;
    }
 
    private void btnPagarClic() {
        if (itemsVenta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto antes de pagar.");
            return;
        }
 
        String numDoc = txtNumDoc.getText().trim();
        if (numDoc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el número de documento del cliente.");
            return;
        }
 
        double total = calcularTotalActual();
 
        JComboBox<String> cmbTipoPago = new JComboBox<>(new String[]{"EFECTIVO", "TARJETA", "MIXTO"});
        JTextField txtMontoPagado = new JTextField();
        JPanel panelPago = new JPanel(new GridLayout(3, 2, 5, 5));
        panelPago.add(new JLabel("Total a pagar:"));
        panelPago.add(new JLabel(String.format("S/. %.2f", total)));
        panelPago.add(new JLabel("Tipo de pago:"));
        panelPago.add(cmbTipoPago);
        panelPago.add(new JLabel("Monto pagado (S/.):"));
        panelPago.add(txtMontoPagado);
 
        int opcion = JOptionPane.showConfirmDialog(this, panelPago, "Datos de pago",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }
 
        double montoPagado;
        try {
            montoPagado = Double.parseDouble(txtMontoPagado.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El monto pagado no es un número válido.");
            return;
        }
 
        if (montoPagado < total) {
            JOptionPane.showMessageDialog(this, "El monto pagado es menor al total a pagar.");
            return;
        }
 
        double vuelto = montoPagado - total;
 
        Venta venta = new Venta();
        venta.setIdUsuario(Sesion.idUsuarioActual);
        venta.setTotal(total);
 
        Comprobante comprobante = new Comprobante();
        comprobante.setIdTipoDoc(cmbDoc.getSelectedIndex() + 1);
        comprobante.setNumDoc(numDoc);
        comprobante.setTipoPago((String) cmbTipoPago.getSelectedItem());
        comprobante.setMontoPagado(montoPagado);
        comprobante.setVuelto(vuelto);
 
        String mensaje = ventaController.registrarVenta(venta, itemsVenta, comprobante);
 
        if (!mensaje.contains("Error")) {
            imprimirBoleta(venta, comprobante);
            String mensajeResaltado = "<html><div style='width:260px;'>"
                    + mensaje.replace("Vuelto:", "<br><span style='font-size:18px; color:#1a7a1a;'><b>Vuelto:")
                    + "</b></span></div></html>";
            JOptionPane.showMessageDialog(this, mensajeResaltado);
            limpiarTicketCompleto();
        } else {
            JOptionPane.showMessageDialog(this, mensaje);
        }
    }

    private void imprimirBoleta(Venta venta, Comprobante comprobante) {
        String nombreCajero = "Cajero #" + venta.getIdUsuario();
        Usuario cajero = usuarioController.buscarPorId(venta.getIdUsuario());
        if (cajero != null) {
            nombreCajero = cajero.getNombre() + " " + cajero.getApellido();
        }
 
        String tipoDoc = (String) cmbDoc.getSelectedItem();
        String fechaHora = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
 
        String linea = "------------------------------------------------";
 
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(centrar("AHORRAMAX", linea.length())).append("\n");
        sb.append(centrar("Bodega - Sistema de Ventas", linea.length())).append("\n");
        sb.append(linea).append("\n");
        sb.append(String.format("N. de Venta : %d%n", venta.getId()));
        sb.append(String.format("Fecha       : %s%n", fechaHora));
        sb.append(String.format("Cajero      : %s%n", nombreCajero));
        sb.append(String.format("Cliente     : %s %s%n", tipoDoc, comprobante.getNumDoc()));
        sb.append(linea).append("\n");
        sb.append(String.format("%-10s %-18s %4s %8s%n", "CODIGO", "DESCRIPCION", "CANT", "SUBTOT"));
        sb.append(linea).append("\n");
 
        for (ItemVenta item : itemsVenta) {
            sb.append(String.format("%-10s %-18s %4d %8.2f%n",
                    item.getCodigo(),
                    recortar(item.getDescripcion(), 18),
                    item.getCantidad(),
                    item.getSubtotal()));
        }
 
        sb.append(linea).append("\n");
        sb.append(String.format("%-33s S/. %8.2f%n", "TOTAL A PAGAR", venta.getTotal()));
        sb.append(String.format("FORMA DE PAGO : %s%n", comprobante.getTipoPago()));
        sb.append(String.format("%-33s S/. %8.2f%n", "MONTO PAGADO", comprobante.getMontoPagado()));
        sb.append(String.format("%-33s S/. %8.2f%n", "VUELTO", comprobante.getVuelto()));
        sb.append(linea).append("\n");
        sb.append(centrar("¡Gracias por su compra!", linea.length())).append("\n");
        sb.append("\n");
 
        System.out.println(sb.toString());
    }
 
    private String centrar(String texto, int ancho) {
        if (texto.length() >= ancho) {
            return texto;
        }
        int espacios = (ancho - texto.length()) / 2;
        return " ".repeat(espacios) + texto;
    }
 
    private String recortar(String texto, int maxLargo) {
        if (texto == null) {
            return "";
        }
        return texto.length() <= maxLargo ? texto : texto.substring(0, maxLargo - 1) + ".";
    }
 
    private void limpiarTicketCompleto() {
        itemsVenta.clear();
        ((DefaultTableModel) tablaVenta.getModel()).setRowCount(0);
        txtNumDoc.setText("");
        txtTotalPagar.setText("S/. 0.00");
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

        btnSalir = new javax.swing.JButton();
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
        jLabel3 = new javax.swing.JLabel();
        txtTotalPagar = new javax.swing.JTextField();
        btnPagar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ProyectoPOS - Caja");
        setMinimumSize(new java.awt.Dimension(720, 480));

        btnSalir.setBackground(new java.awt.Color(51, 51, 51));
        btnSalir.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("SALIR");
        btnSalir.setMargin(new java.awt.Insets(4, 28, 6, 28));
        btnSalir.addActionListener(this::btnSalirActionPerformed);
        getContentPane().add(btnSalir, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(178, 229, 255));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Documento Identidad:");

        cmbDoc.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbDoc.setBorder(null);

        txtNumDoc.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtNumDoc.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtNumDoc.setMargin(new java.awt.Insets(2, 8, 2, 8));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Producto:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Cantidad:");

        txtIdProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtIdProd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtIdProd.setMargin(new java.awt.Insets(2, 10, 2, 10));

        spiCant.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        spiCant.setBorder(null);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel8.setText("Numero documento:");

        btnAgregarProd.setBackground(new java.awt.Color(62, 191, 214));
        btnAgregarProd.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnAgregarProd.setText("AGREGAR");
        btnAgregarProd.setMargin(new java.awt.Insets(4, 28, 6, 28));
        btnAgregarProd.addActionListener(this::btnAgregarProdActionPerformed);

        btnQuitarProd.setBackground(new java.awt.Color(218, 55, 55));
        btnQuitarProd.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnQuitarProd.setText("QUITAR");
        btnQuitarProd.setMargin(new java.awt.Insets(4, 28, 6, 28));
        btnQuitarProd.addActionListener(this::btnQuitarProdActionPerformed);

        tablaVenta.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
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
        tablaVenta.setRowHeight(35);
        tablaVenta.setRowMargin(10);
        jScrollPane1.setViewportView(tablaVenta);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Total a Pagar");

        txtTotalPagar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        txtTotalPagar.setForeground(new java.awt.Color(255, 51, 51));
        txtTotalPagar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtTotalPagar.setMargin(new java.awt.Insets(2, 10, 2, 10));

        btnPagar.setBackground(new java.awt.Color(126, 214, 62));
        btnPagar.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnPagar.setText("Pagar");
        btnPagar.setMargin(new java.awt.Insets(4, 28, 6, 28));
        btnPagar.addActionListener(this::btnPagarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(cmbDoc, 0, 175, Short.MAX_VALUE)
                            .addComponent(txtNumDoc))
                        .addGap(112, 112, 112)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(spiCant, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(btnAgregarProd)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnQuitarProd)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 260, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPagar)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(44, 44, 44))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbDoc, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(txtIdProd))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spiCant, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarProd)
                    .addComponent(btnQuitarProd))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addGap(131, 131, 131)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPagar))
                .addGap(41, 41, 41))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();
        vistas.Menu ventanaMenu = new vistas.Menu();
        ventanaMenu.setVisible(true);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnAgregarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProdActionPerformed
        agregarProducto();
    }//GEN-LAST:event_btnAgregarProdActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        btnPagarClic();
    }//GEN-LAST:event_btnPagarActionPerformed

    private void btnQuitarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarProdActionPerformed
        quitarProductoSeleccionado();
    }//GEN-LAST:event_btnQuitarProdActionPerformed



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
