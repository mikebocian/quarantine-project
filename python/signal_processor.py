from scipy.ndimage import uniform_filter1d

import sampler
import numpy as np


class SignalProcessor:

    def analyze(self, sampling_freq, samples):
        (left, right) = samples.transpose()
        left_fft = np.array_split(np.abs(np.fft.fft(left, norm='ortho')), 2)[0]
        right_fft = np.array_split(np.abs(np.fft.fft(right, norm='ortho')), 2)[0]

        freqs = np.split(np.fft.fftfreq(int(samples.size / 2)) * sampling_freq, 2)[0]

        return freqs, uniform_filter1d(left_fft, size=2), uniform_filter1d(right_fft, size=10)

