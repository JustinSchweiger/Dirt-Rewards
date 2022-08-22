package net.dirtcraft.plugins.dirtrewards.utils.gradient;

@FunctionalInterface
public interface Interpolator {

	double[] interpolate(double from, double to, int max);
}
