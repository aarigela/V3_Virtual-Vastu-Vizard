/**
 * ComponentData.java - Encapsulates information about Components
 */

package v3;

import java.awt.Color;

public class ComponentData
{
	private String componentName = "Component";
	private float componentLength = 0.0f;
	private float componentBreadth = 0.0f;
	private float componentHeight = 0.0f;
	private Color componentColor = null;

	public ComponentData( )
	{
		componentName = "Component";
		componentLength = 0.0f;
		componentBreadth = 0.0f;
		componentHeight = 0.0f;
		componentColor = Color.white;
	}

	public ComponentData(String name, float length, float breadth,
			float height, Color color)
	{
		componentName = name;
		componentLength = length;
		componentBreadth = breadth;
		componentHeight = height;
		componentColor = color;
	}

	public String getComponentName( )
	{
		return componentName;
	}

	public void setComponentName(String name)
	{
		componentName = name;
	}

	public float getComponentLength( )
	{
		return componentLength;
	}

	public void setComponentLength(float length)
	{
		componentLength = length;
	}

	public float getComponentBreadth( )
	{
		return componentBreadth;
	}

	public void setComponentBreadth(float breadth)
	{
		componentBreadth = breadth;
	}

	public float getComponentHeight( )
	{
		return componentHeight;
	}

	public void setComponentHeight(float height)
	{
		componentHeight = height;
	}

	public Color getComponentColor( )
	{
		return componentColor;
	}

	public void setComponentColor(Color color)
	{
		componentColor = color;
	}

	public void getComponentColor(float[] color)
	{
		componentColor.getColorComponents(color);
	}

	public String toString( )
	{
		String value = null;
		value = componentName + "" + componentLength + "" + componentBreadth
				+ "" + componentHeight + "" + componentColor;
		return value;
	}
}
