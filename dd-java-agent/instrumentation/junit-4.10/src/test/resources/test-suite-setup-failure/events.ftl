[ {
  "type" : "test_session_end",
  "version" : 1,
  "content" : {
    "test_session_id" : ${content_test_session_id},
    "service" : "worker.org.gradle.process.internal.worker.gradleworkermain",
    "name" : "junit.test_session",
    "resource" : "junit-4.10",
    "start" : ${content_start},
    "duration" : ${content_duration},
    "error" : 0,
    "metrics" : {
      "process_id" : ${content_metrics_process_id},
      "_dd.profiling.enabled" : 0,
      "_dd.trace_span_attribute_schema" : 0
    },
    "meta" : {
      "test.type" : "test",
      "os.architecture" : ${content_meta_os_architecture},
      "test.status" : "fail",
      "language" : "jvm",
      "runtime.name" : ${content_meta_runtime_name},
      "runtime.vendor" : ${content_meta_runtime_vendor},
      "env" : "none",
      "os.platform" : ${content_meta_os_platform},
      "dummy_ci_tag" : "dummy_ci_tag_value",
      "os.version" : ${content_meta_os_version},
      "library_version" : ${content_meta_library_version},
      "component" : "junit",
      "_dd.profiling.ctx" : "test",
      "span.kind" : "test_session_end",
      "runtime.version" : ${content_meta_runtime_version},
      "runtime-id" : ${content_meta_runtime_id},
      "test.command" : "junit-4.10",
      "test.framework_version" : ${content_meta_test_framework_version},
      "test.framework" : "junit4"
    }
  }
}, {
  "type" : "test_module_end",
  "version" : 1,
  "content" : {
    "test_session_id" : ${content_test_session_id},
    "test_module_id" : ${content_test_module_id},
    "service" : "worker.org.gradle.process.internal.worker.gradleworkermain",
    "name" : "junit.test_module",
    "resource" : "junit-4.10",
    "start" : ${content_start_2},
    "duration" : ${content_duration_2},
    "error" : 0,
    "metrics" : { },
    "meta" : {
      "test.type" : "test",
      "os.architecture" : ${content_meta_os_architecture},
      "test.module" : "junit-4.10",
      "test.status" : "fail",
      "runtime.name" : ${content_meta_runtime_name},
      "runtime.vendor" : ${content_meta_runtime_vendor},
      "env" : "none",
      "os.platform" : ${content_meta_os_platform},
      "dummy_ci_tag" : "dummy_ci_tag_value",
      "os.version" : ${content_meta_os_version},
      "library_version" : ${content_meta_library_version},
      "component" : "junit",
      "span.kind" : "test_module_end",
      "runtime.version" : ${content_meta_runtime_version},
      "test.framework_version" : ${content_meta_test_framework_version},
      "test.framework" : "junit4"
    }
  }
}, {
  "type" : "test_suite_end",
  "version" : 1,
  "content" : {
    "test_session_id" : ${content_test_session_id},
    "test_module_id" : ${content_test_module_id},
    "test_suite_id" : ${content_test_suite_id},
    "service" : "worker.org.gradle.process.internal.worker.gradleworkermain",
    "name" : "junit.test_suite",
    "resource" : "org.example.TestFailedSuiteSetup",
    "start" : ${content_start_3},
    "duration" : ${content_duration_3},
    "error" : 1,
    "metrics" : { },
    "meta" : {
      "test.type" : "test",
      "os.architecture" : ${content_meta_os_architecture},
      "test.source.file" : "dummy_source_path",
      "test.module" : "junit-4.10",
      "test.status" : "fail",
      "runtime.name" : ${content_meta_runtime_name},
      "runtime.vendor" : ${content_meta_runtime_vendor},
      "env" : "none",
      "os.platform" : ${content_meta_os_platform},
      "dummy_ci_tag" : "dummy_ci_tag_value",
      "os.version" : ${content_meta_os_version},
      "library_version" : ${content_meta_library_version},
      "component" : "junit",
      "error.type" : "java.lang.RuntimeException",
      "span.kind" : "test_suite_end",
      "error.message" : ${content_meta_error_message},
      "test.suite" : "org.example.TestFailedSuiteSetup",
      "error.stack" : ${content_meta_error_stack},
      "runtime.version" : ${content_meta_runtime_version},
      "test.framework_version" : ${content_meta_test_framework_version},
      "test.framework" : "junit4"
    }
  }
} ]