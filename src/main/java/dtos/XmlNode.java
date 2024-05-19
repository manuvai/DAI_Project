package dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XmlNode {

	private String name;
	private String content;

	private List<XmlNode> nodes = new ArrayList<>();

	public XmlNode(final String inName) {
		this(inName, (XmlNode) null);
	}

	public XmlNode(final String inName, final String inContent) {
		name = inName;
		content = inContent;
	}

	public XmlNode(final String inName, final XmlNode inNode) {
		name = inName;
		addNode(inNode);

	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public List<XmlNode> getNodes() {
		return nodes;
	}

	public void addNode(final XmlNode inNode) {
		if (Objects.nonNull(inNode)) {
			nodes.add(inNode);
		}
	}

	@Override
	public String toString() {
		final StringBuilder response = new StringBuilder();

		response.append("<" + name + ">");

		if (Objects.nonNull(content)) {
			response.append(content);
		}

		if (nodes.size() > 0) {
			final List<String> nodesContents = nodes.stream()
					.map(XmlNode::toString)
					.toList();

			response.append(String.join("", nodesContents));
		}

		response.append("</" + name + ">");

		return response.toString();
	}

}