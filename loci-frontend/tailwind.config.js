module.exports = {
  content: [
    './src/**/*.{html,ts,css,scss}', // Angular templates & components
    './src/styles.scss',
    './src/index.html',
  ],
  theme: {
    extend: {
      colors: {
        transparent: 'transparent',
        current: 'currentColor',

        // --- App palette from the image ---
        background: '#000000',     // main page background
        surface: '#242423',        // cards / panels
        'surface-soft': '#000000', // darker variant if needed

        // text colors
        text: {
          DEFAULT: '#F6F6F6',      // main text
          soft: '#EBEDF0',         // subtle text / secondary
          muted: '#767C8C',        // captions / placeholders
          inverse: '#000000',      // text on light elements
        },

        // borders / separators
        border: '#EBEDF0',

        // === PRIMARY (for main actions, etc.) ===
        primary: {
          50: '#f9fafb',
          100: '#f3f4f6',
          200: '#e5e7eb',
          300: '#d1d5db',
          400: '#9ca3af',
          500: '#6b7280',
          600: '#4b5563',
          700: '#374151',
          800: '#1f2937',
          900: '#111827',
          DEFAULT: '#111827',
          focus: '#020617',
          content: '#ffffff',
        },

        // === NEUTRAL (borders, cards, backgrounds) ===
        neutral: {
          50: '#f9fafb',
          100: '#f3f4f6',
          200: '#e5e7eb',
          300: '#e5e7eb',
          400: '#d1d5db',
          500: '#9ca3af',
          600: '#6b7280',
          700: '#4b5563',
          800: '#374151',
          900: '#111827',
          DEFAULT: '#f5f7fb',
          focus: '#e5e7eb',
          content: '#111827',
        },

        // === SECONDARY ===
        secondary: {
          50: '#eff6ff',
          100: '#dbeafe',
          200: '#bfdbfe',
          300: '#93c5fd',
          400: '#60a5fa',
          500: '#3b82f6',
          600: '#2563eb',
          700: '#1d4ed8',
          800: '#1e40af',
          900: '#1e3a8a',
          DEFAULT: '#3b82f6',
          focus: '#1d4ed8',
          content: '#ffffff',
        },

        // === INFO ===
        info: {
          50: '#f0f9ff',
          100: '#e0f2fe',
          200: '#bae6fd',
          300: '#7dd3fc',
          400: '#38bdf8',
          500: '#0ea5e9',
          600: '#0284c7',
          700: '#0369a1',
          800: '#075985',
          900: '#0c4a6e',
          DEFAULT: '#0ea5e9',
          focus: '#0369a1',
          content: '#ffffff',
        },

        // === SUCCESS ===
        success: {
          50: '#f0fdf4',
          100: '#dcfce7',
          200: '#bbf7d0',
          300: '#86efac',
          400: '#4ade80',
          500: '#22c55e',
          600: '#16a34a',
          700: '#15803d',
          800: '#166534',
          900: '#14532d',
          DEFAULT: '#22c55e',
          focus: '#15803d',
          content: '#ffffff',
        },

        // === WARNING ===
        warning: {
          50: '#fffbeb',
          100: '#fef3c7',
          200: '#fde68a',
          300: '#fcd34d',
          400: '#fbbf24',
          500: '#f59e0b',
          600: '#d97706',
          700: '#b45309',
          800: '#92400e',
          900: '#78350f',
          DEFAULT: '#f59e0b',
          focus: '#b45309',
          content: '#111827',
        },

        // === ERROR / DANGER ===
        error: {
          50: '#fef2f2',
          100: '#fee2e2',
          200: '#fecaca',
          300: '#fca5a5',
          400: '#f87171',
          500: '#ef4444',
          600: '#dc2626',
          700: '#b91c1c',
          800: '#991b1b',
          900: '#7f1d1d',
          DEFAULT: '#ef4444',
          focus: '#b91c1c',
          content: '#ffffff',
        },

        // app-specific
        app: {
          background: '#000000',
        },
        sidebar: {
          bg: '#242423',
          active: '#000000',
          icon: '#F6F6F6',
          'icon-active': '#FFFFFF',
        },
        card: {
          bg: '#242423',
          border: '#EBEDF0',
        },
        input: {
          bg: '#242423',
          border: '#767C8C',
        },
      },

      fontFamily: {
        sans: ['"SF Pro Text"', '-apple-system', 'BlinkMacSystemFont', '"Segoe UI"', 'sans-serif'],
      },
      borderRadius: {
        lg: '0.75rem',
        xl: '1rem',
        '2xl': '1.25rem',
      },
      boxShadow: {
        card: '0 18px 45px rgba(0, 0, 0, 0.6)',
      },
    },
  },
  variants: {
    extend: {
      backgroundColor: ['active', 'group-hover'],
      textColor: ['active', 'group-hover'],
    },
  },
  plugins: [],
};
