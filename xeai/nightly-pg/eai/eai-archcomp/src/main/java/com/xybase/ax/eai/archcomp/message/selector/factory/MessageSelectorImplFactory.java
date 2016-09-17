package com.xybase.ax.eai.archcomp.message.selector.factory;

import com.xybase.ax.eai.archcomp.message.selector.MessageSelectorImpl;
import com.xybase.ax.eai.archcomp.selector.dao.SelectorDaoImpl;

public class MessageSelectorImplFactory {

	private SelectorDaoImpl selectorDaoImpl;

	public MessageSelectorImplFactory() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the iSelectorDaoImpl
	 */
	public SelectorDaoImpl getSelectorDaoImpl() {
		return selectorDaoImpl;
	}

	/**
	 * @param iSelectorDaoImpl
	 *            the iSelectorDaoImpl to set
	 */
	public void setSelectorDaoImpl(SelectorDaoImpl iSelectorDaoImpl) {
		this.selectorDaoImpl = iSelectorDaoImpl;
	}

	public MessageSelectorImpl createNewMessageSelector(int selectorId,
			Object... objects) {
		MessageSelectorImpl result = new MessageSelectorImpl(objects);
		result.setSelectorDao(selectorDaoImpl);
		result.setSelectors(selectorDaoImpl.get(selectorId));
		return result;
	}

}
