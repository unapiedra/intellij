# Description:
#
# Common test utilities for IntelliJ plugins.

package(default_visibility = ["//visibility:public"])

licenses(["notice"])  # Apache 2.0

load(
    "//intellij_platform_sdk:build_defs.bzl",
    "select_for_ide",
    "select_for_plugin_api",
)

java_library(
    name = "lib",
    testonly = 1,
    srcs = glob(["src/**/*.java"]) +
           select_for_ide(
               android_studio = glob(["cidr/src/**/*.java"]),
               clion = glob(["cidr/src/**/*.java"]),
               default = [],
           ) + select_for_plugin_api({
        "android-studio-3.2": glob(["studio/integration_3_2/**/*.java"]),
        "android-studio-3.3": glob(["studio/integration_3_3/**/*.java"]),
        "default": [],
    }),
    deps = [
        "//intellij_platform_sdk:plugin_api_for_tests",
        "//intellij_platform_sdk:jsr305",
        "@junit//jar",
    ] + select_for_ide(
        android_studio = ["//sdkcompat"],
        clion = ["//sdkcompat"],
        default = [],
    ),
)
