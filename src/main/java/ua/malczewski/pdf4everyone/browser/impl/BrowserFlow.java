package ua.malczewski.pdf4everyone.browser.impl;

import com.google.common.collect.Lists;

import java.util.List;

public class BrowserFlow {

	public static BrowserFlowBuilder builder() {
		return new BrowserFlowBuilder();
	}

	private List<BrowserPrepareAction> steps = Lists.newArrayList();
	private BrowserTerminateAction action;

	public byte[] process(Browser browser) {
		try {
			steps.forEach(step -> step.accept(browser));
			return action.apply(browser);
		} finally {
			browser.destroy();
		}
	}

	public static class BrowserFlowBuilder {
		private final BrowserFlow flow = new BrowserFlow();

		private BrowserFlowBuilder() {}

		public BrowserFlowBuilder addStep(BrowserPrepareAction action) {
			flow.steps.add(action);
			return this;
		}
		public BrowserFlowBuilder addStepIf(boolean condition, BrowserPrepareAction action) {
			if (condition)
				flow.steps.add(action);
			return this;
		}

		public BrowserFlow perform(BrowserTerminateAction action) {
			flow.action = action;
			return flow;
		}
	}
}
