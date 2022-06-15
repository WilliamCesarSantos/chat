package com.santos.will.chat.server.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.santos.will.chat.dto.Receiver;

public class ServerView extends JFrame {

	public static final Long DEFAULT_REFRESH_RATE_CLIENT_LIST = 1L;
	
	private JPanel contentPane;
	private JList<String> jlClientsConnected;
	private JToggleButton jtglStatus;

	/**
	 * Create the frame.
	 */
	public ServerView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("Server status");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblClientesConectados = new JLabel("Clientes conectados:");
		GridBagConstraints gbc_lblClientesConectados = new GridBagConstraints();
		gbc_lblClientesConectados.insets = new Insets(0, 0, 5, 5);
		gbc_lblClientesConectados.gridx = 8;
		gbc_lblClientesConectados.gridy = 0;
		contentPane.add(lblClientesConectados, gbc_lblClientesConectados);
		
		JLabel lblNewLabel_1 = new JLabel("Localhost:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		jtglStatus = new JToggleButton("Ativo");
		jtglStatus.setEnabled(false);
		jtglStatus.setForeground(Color.BLACK);
		GridBagConstraints gbc_tglbtnNewToggleButton = new GridBagConstraints();
		gbc_tglbtnNewToggleButton.gridwidth = 4;
		gbc_tglbtnNewToggleButton.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnNewToggleButton.gridx = 2;
		gbc_tglbtnNewToggleButton.gridy = 1;
		contentPane.add(jtglStatus, gbc_tglbtnNewToggleButton);
		
		jlClientsConnected = new JList<String>();
		jlClientsConnected.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
		jlClientsConnected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridheight = 8;
		gbc_list.gridwidth = 4;
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 8;
		gbc_list.gridy = 1;
		contentPane.add(jlClientsConnected, gbc_list);
	}
	
	public void updateReceivers(final List<Receiver> receivers) {
		Objects.requireNonNull(receivers, "receivers cannot be null");
		this.jlClientsConnected.setModel(new ClientsConnectedModel(receivers));
	}

	public void updateStatusServer(final boolean status) {
		if(status) {
			jtglStatus.setText("Ativo");
		} else {
			jtglStatus.setText("Inativo");
		}
	}
	private class ClientsConnectedModel extends AbstractListModel<String> {

		private final List<Receiver> receivers;
		
		public ClientsConnectedModel(final List<Receiver> receivers) {
			this.receivers =  receivers;
		}
		
		@Override
		public String getElementAt(int index) {
			final Receiver receiver = this.receivers.get(index);
			return format(receiver);
		}

		@Override
		public int getSize() {
			return this.receivers.size();
		}
		
		private String format(final Receiver receiver) {
			return String.format("%s connected by %s:%d", receiver.getName(), receiver.getIp(), receiver.getPort());
		}
	}
}
