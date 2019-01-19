package com.santos.will.chat.client.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.santos.will.chat.dto.Receiver;

public class ChatView extends JFrame {

	private JTextField txtMessage;
	private DefaultListModel<String> model;
	private final ChatController controller;
	private JComboBox<String> jcbReceivers;
	private List<Receiver> receivers = Collections.emptyList();

	public ChatView(final ChatController controller) {
		Objects.requireNonNull(controller, "controller cannot be null");
		setSize(400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.controller = controller;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JList<String> list = new JList<>();
		model = new DefaultListModel<>();
		list.setModel(model);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(list);
		getContentPane().add(scroll, gbc_list);

		JLabel lblHost = new JLabel("Destinat\u00E1rio:");
		GridBagConstraints gbc_lblHost = new GridBagConstraints();
		gbc_lblHost.anchor = GridBagConstraints.EAST;
		gbc_lblHost.insets = new Insets(0, 0, 5, 5);
		gbc_lblHost.gridx = 0;
		gbc_lblHost.gridy = 1;
		getContentPane().add(lblHost, gbc_lblHost);

		jcbReceivers = new JComboBox<>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		getContentPane().add(jcbReceivers, gbc_comboBox);
		this.jcbReceivers.setModel(new ReceiverModel());

		JLabel lblMessage = new JLabel("Mensagem:");
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.insets = new Insets(0, 0, 0, 5);
		gbc_lblMessage.anchor = GridBagConstraints.EAST;
		gbc_lblMessage.gridx = 0;
		gbc_lblMessage.gridy = 2;
		getContentPane().add(lblMessage, gbc_lblMessage);

		txtMessage = new JTextField();
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMessage.gridx = 1;
		gbc_txtMessage.gridy = 2;
		getContentPane().add(txtMessage, gbc_txtMessage);
		txtMessage.setColumns(10);
		txtMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					ChatView.this.controller.sendMessage(txtMessage.getText());
					txtMessage.setText(null);
				}
			}
		});
	}

	public DefaultListModel<String> getModel() {
		return this.model;
	}

	public String getMessage() {
		return txtMessage.getText();
	}

	public Receiver getReceiver() {
		final Object object = this.jcbReceivers.getModel().getSelectedItem();
		return Optional.ofNullable(object)
				.map(name -> this.receivers.stream()
						.filter(receiver -> name.equals(receiver.getName()))
						.findAny()
						.orElse(null))
				.orElse(null);
	}

	protected void updateReceivers(final List<Receiver> receivers) {
		Objects.requireNonNull(receivers, "receivers cannot be null");
		this.receivers = receivers;
		((ReceiverModel) jcbReceivers.getModel()).fireContentsChanged();
	}

	private class ReceiverModel extends AbstractListModel<String> implements ComboBoxModel<String> {

		private static final long serialVersionUID = 1L;
		private Object selected;

		@Override
		public String getElementAt(int index) {
			final Receiver receiver = ChatView.this.receivers.get(index);
			return receiver.getName();
		}

		protected void fireContentsChanged() {
			super.fireContentsChanged(this, 0, ChatView.this.receivers.size());
		}

		@Override
		public int getSize() {
			return ChatView.this.receivers.size();
		}

		@Override
		public Object getSelectedItem() {
			return this.selected;
		}

		@Override
		public void setSelectedItem(Object selected) {
			this.selected = selected;
		}

	}
}
